package databaseUnit;

import com.opencsv.bean.CsvBindByPosition;

public class CsvBean {
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getSql1() {
		return sql1;
	}
	public void setSql1(String sql1) {
		this.sql1 = sql1;
	}
	public String getSql2() {
		return sql2;
	}
	public void setSql2(String sql2) {
		this.sql2 = sql2;
	}
	public String getSql3() {
		return sql3;
	}
	public void setSql3(String sql3) {
		this.sql3 = sql3;
	}
	public String getSql4() {
		return sql4;
	}
	public void setSql4(String sql4) {
		this.sql4 = sql4;
	}
	public String getSql5() {
		return sql5;
	}
	public void setSql5(String sql5) {
		this.sql5 = sql5;
	}
	public String getSql6() {
		return sql6;
	}
	public void setSql6(String sql6) {
		this.sql6 = sql6;
	}
	public String getSql7() {
		return sql7;
	}
	public void setSql7(String sql7) {
		this.sql7 = sql7;
	}
	public String getSql8() {
		return sql8;
	}
	public void setSql8(String sql8) {
		this.sql8 = sql8;
	}
	public String getSql9() {
		return sql9;
	}
	public void setSql9(String sql9) {
		this.sql9 = sql9;
	}
	public String getSql10() {
		return sql10;
	}
	public void setSql10(String sql10) {
		this.sql10 = sql10;
	}
	@CsvBindByPosition(position = 1)
    private String correo;
	@CsvBindByPosition(position = 3)
    private String sql1;
	@CsvBindByPosition(position = 4)
	private String sql2;
	@CsvBindByPosition(position = 5)
	private String sql3;
	@CsvBindByPosition(position = 6)
	private String sql4;
	@CsvBindByPosition(position = 7)
	private String sql5;
	@CsvBindByPosition(position = 8)
	private String sql6;
	@CsvBindByPosition(position = 9)
	private String sql7;
	@CsvBindByPosition(position = 10)
	private String sql8;
	@CsvBindByPosition(position = 11)
	private String sql9;
	@CsvBindByPosition(position = 12)
	private String sql10;
}
