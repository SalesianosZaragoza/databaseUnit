package databaseUnit;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Testcontainers
class MysqlTest {
	private static final String POKEMON_SQL = "./pokemon.sql";
	private static final String RESPUESAS_CSV = "respuestas.csv";
	private static final String sql2 = "SELECT * FROM pokemon";

	private static final Logger logger = LoggerFactory.getLogger(MysqlTest.class);
	private static IDatabaseTester tester = null;
	DbUnitAssert assertUtil = new DbUnitAssert();
	static MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:8.0")).withUsername("root")
			.withPassword("test").withDatabaseName("pokemondb").withExposedPorts(3306).withInitScript(POKEMON_SQL)
			.withLogConsumer(new Slf4jLogConsumer(logger));
	static List<CsvBean> readAllCsv;
	static IDatabaseConnection databaseConnection;

	@Test
	void testQuery() throws SQLException, DatabaseUnitException {
		String sqlsolucion = null;
		Optional<CsvBean> rowSolution = readAllCsv.stream().filter((b) -> b.getCorreo().contains("gorka.sanz"))
				.findFirst();
		sqlsolucion = rowSolution.get().getSql1();
		logger.debug(sqlsolucion);

		ITable expectedTable = databaseConnection.createQueryTable("expected", sql2);
		ITable actualTable = databaseConnection.createQueryTable("expected", sql2);

		Column[] expectedColumns = expectedTable.getTableMetaData().getColumns();
		ITable sortedExpected = new SortedTable(expectedTable, expectedColumns);
		ITable sortedActual = new SortedTable(actualTable, expectedColumns);

		assertUtil.assertEquals(sortedExpected, sortedActual);
	}

	private static IDatabaseConnection initilaze(MySQLContainer<?> container) throws Exception {
		container.start();
		tester = initDatabaseTester(container);
		IDatabaseConnection databaseConnection = tester.getConnection();
		return databaseConnection;
	}

	public static List<CsvBean> beanBuilderExample(Path path, Class clazz) throws Exception {
		CsvTransfer csvTransfer = new CsvTransfer();

		try (Reader reader = Files.newBufferedReader(path)) {
			CsvToBean<CsvBean> cb = new CsvToBeanBuilder<CsvBean>(reader).withType(clazz).build();

			csvTransfer.setCsvList(cb.parse());
		}
		return csvTransfer.getCsvList();
	}

	public static List<CsvBean> readAllExample() throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource(RESPUESAS_CSV).toURI());
		return beanBuilderExample(path, CsvBean.class);
	}

	private static IDatabaseTester initDatabaseTester(MySQLContainer<?> container) throws Exception {
		logger.debug(container.getJdbcUrl());
		JdbcDatabaseTester tester = new JdbcDatabaseTester(container.getDriverClassName(), container.getJdbcUrl(),
				container.getUsername(), container.getPassword());
		return tester;
	}

	@AfterAll
	public static void tearDown() throws Exception {
	}

	@BeforeAll
	public static void setup() {
		try {
			readAllCsv = readAllExample();
			databaseConnection = initilaze(container);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
