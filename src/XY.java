import java.util.ArrayList;
import java.util.Random;

public class XY {
	private ArrayList<Double> x;
	private ArrayList<Double> y;
	private ArrayList<Double> y_solve;
	// for y=a*x+b
	private double a;
	private double b;
	// for y=a0*exp(a1*x)
	private double a0;
	private double a1;
	// for y=a*ln(x)+b
	private double pl_a;
	private double pl_b;

	public XY() {
		this.x = new ArrayList<Double>();
		this.y = new ArrayList<Double>();
		this.y_solve = new ArrayList<Double>();
		
		
	}

	public XY(int n) {

		this.x = new ArrayList<Double>();
		this.y = new ArrayList<Double>();
		this.y_solve = new ArrayList<Double>();
        this.y_solve.clear();
		for (int i = 0; i < n; i++) {
			x.add(0.0);
			y.add(0.0);
			y_solve.add(0.0);
		}

	}
	
	public void setY_solve_null() {
		this.y_solve.clear();
		for (int i = 0; i < x.size(); i++) {
			y_solve.add(0.0);
		}
		
	}

	public double math(double x) {
		return Math.ceil(x * 1000) / 1000;
	}

	public ArrayList<Double> getY_solve() {
		return y_solve;
	}

	public void setXYr(int n) {
		Random r = new Random();
		this.x = new ArrayList<Double>();
		this.y = new ArrayList<Double>();
		this.y_solve = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			x.add(math(r.nextDouble() * 10));
			y.add(math(r.nextDouble() * 10));
			y_solve.add(0.0);
		}
	}

	public ArrayList<Double> getX() {
		return x;
	}

	public void setX(ArrayList<Double> x) {
		this.x = x;
	}

	public void setY_solve(ArrayList<Double> y) {
		this.y_solve = y;
	}

	public ArrayList<Double> getY() {
		return y;
	}

	public void setY(ArrayList<Double> y) {
		this.y = y;
	}

	public int colX() {
		return x.size();
	}

	public int colY() {
		return y.size();
	}

	public double sumX() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += x.get(i);
		}
		return sum;
	}

	public double sumY() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += y.get(i);
		}
		return sum;
	}

	public double sumX2() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.pow(x.get(i), 2);
		}
		return sum;
	}

	public double sumY2() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.pow(y.get(i), 2);
		}
		return sum;

	}

	public double sumXY() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += y.get(i) * x.get(i);
		}
		return sum;

	}

	public double correlation() {
		double r = (sumXY() - sumX() * sumY() / colX())
				/ Math.sqrt((sumX2() - sumX() * sumX() / colX()) * (sumY2() - sumY() * sumY() / colX()));
		return r;

	}

	public double chislCorrel_pln() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += (Math.log(x.get(i)) - (sumlnX() / colX())) * (y.get(i) - (sumY() / colY()));
		}
		return sum;

	}

	public double znamenCorrel_pln() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.pow(Math.log(x.get(i)) - sumlnX() / colX(), 2);
		}
		double sum2 = 0;
		for (int i = 0; i < y.size(); i++) {
			sum2 += Math.pow(y.get(i) - sumY() / colY(), 2);
		}

		return Math.sqrt(sum * sum2);
	}

	public double correlation_pln() {
		return chislCorrel_pln() / znamenCorrel_pln();
	}

	public void calculateA() {
		this.a = sumY() / colY() - b * sumX() / colX();

	}

	public double getA() {
		return a;
	}

	public void calculateB() {
		this.b = (colX() * sumXY() - sumX() * sumY()) / (colX() * sumX2() - sumX() * sumX());

	}

	public double getB() {
		return b;
	}

	public double sumlnY() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.log(Math.abs(y.get(i)));
		}
		return sum;

	}

	public double sumlnYX() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.abs(Math.log(Math.abs(y.get(i)))) * x.get(i);
		}
		return sum;
	}

	public double b1() {
		return (colX() * (sumlnYX()) - sumlnY() * sumX()) / (colX() * sumX2() - sumX() * sumX());
	}

	public double b0() {
		return (sumlnY() - b1() * sumX()) / colX();
	}

	public void calculateA0() {
		this.a0 = Math.exp(b0());
	}

	public double getA0() {
		return a0;
	}

	public void calculateA1() {
		this.a1 = b1();

	}

	public double getA1() {
		return a1;
	}

	public double sumlnYi_solveSublnYi2() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.pow(Math.log(y_solve.get(i)) - Math.log(y.get(i)), 2);
			;
		}
		return sum;

	}

	public double sumlnX() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.log(x.get(i));
		}
		return sum;
	}

	public double sumlnX2() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.pow(Math.log(x.get(i)), 2);
		}
		return sum;
	}

	public double sumlnXY() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.log(x.get(i)) * y.get(i);
		}
		return sum;

	}

	public double det() {
		return sumlnX2() * colX() - sumlnX() * sumlnX();
	}

	public double deta() {
		return sumlnXY() * colX() - sumY() * sumlnX();
	}

	public double detb() {
		return sumlnX2() * sumY() - sumlnX() * sumlnXY();
	}

	public void calculatePl_A() {
		this.pl_a = deta() / det();
	}

	public void calculatePl_B() {
		this.pl_b = detb() / det();
	}

	public double getPl_b() {
		return pl_a;
	}

	public double getPl_a() {
		return pl_b;
	}

	public double sumlnXisubaverlnX2() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.pow(Math.log(x.get(i)) - sumlnX() / colX(), 2);
		}
		return sum;

	}

	public void calclLineY() {
		calculateB();
		calculateA();
		y_solve.clear();
		for (int i = 0; i < x.size(); i++) {
			y_solve.add(math(a + b * x.get(i)));
		}
	}

	public void calclExpY() {
		calculateA1();
		calculateA0();
		y_solve.clear();
		for (int i = 0; i < y.size(); i++) {
			y_solve.add(math(a0 * Math.exp(a1 * x.get(i))));
		}
	}

	public void calclLnY() {
		calculatePl_A();
		calculatePl_B();
		y_solve.clear();
		for (int i = 0; i < y.size(); i++) {
			y_solve.add(math(pl_a * Math.log(x.get(i)) + pl_b));
		}
	}

	public double sumYsubY_solve2() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.pow(y_solve.get(i) - y.get(i), 2);
		}
		return sum;

	}

	public double averageY() {
		return sumY() / colY();
	}

	public double sumYsubAverY2() {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += Math.pow(y.get(i) - averageY(), 2);
		}
		return sum;

	}

	public double R2() {
		return 1 - sumYsubY_solve2() / sumYsubAverY2();
	}

	public double sumXisubXaver2() {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += Math.pow(x.get(i) - sumX() / colX(), 2);
		}
		return sum;

	}

	public double sb_line() {
		return Math.sqrt((sumYsubY_solve2() / (colY() - 2)) / sumXisubXaver2());

	}

	public double sa_line() {
		return Math.sqrt((sumYsubY_solve2() * sumX2()) / ((colY() - 2) * colX() * sumXisubXaver2()));

	}

	public double sb_exp() {
		return Math.sqrt((sumlnYi_solveSublnYi2() / (colY() - 2)) / sumXisubXaver2());

	}

	public double sa_exp() {
		return Math.sqrt((sumlnYi_solveSublnYi2() * sumX2()) / ((colX() - 2) * colX() * sumXisubXaver2()));

	}

	public double sb_pln() {
		return Math.sqrt((sumYsubY_solve2() / (colY() - 2)) / sumlnXisubaverlnX2());

	}

	public double sa_pln() {
		return Math.sqrt((sumYsubY_solve2() * sumlnX2()) / ((colY() - 2) * colX() * sumlnXisubaverlnX2()));

	}

	public double ta(double a, double sa) {
		return a / sa;
	}

	public double tb(double b, double sb) {
		return b / sb;
	}

	public double tnabl(double cor) {
		return cor*Math.sqrt(colX()-2)/Math.sqrt(1-Math.pow(cor, 2));
	}
	
	
	
public String checkResultB( double tb, double tc) {
		
	if(tb > tc) 
		return "Коэффициент регрессии B является значимым";
	else  
		return "Значимость коэффициента регрессии B не подтверждается";
	}
	
public String checkResultA(double ta, double tc) {
	
	if(ta > tc) 
		return "Коэффициент регрессии A является значимым";
	else  
		return "Значимость коэффициента регрессии A не подтверждается";
	}
}
