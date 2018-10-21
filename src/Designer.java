import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jfree.ui.RefineryUtilities;

public class Designer extends JFrame{

	private JFrame frame;
	private JTable table;
	private int n = 0;
	private XY xy;
	private File file;
	private ParseCSV p;
	private int num;

	private double ax, bx, yx;
	private JFileChooser fileChooser = new JFileChooser();
	private String title = "График", cor, det, a, b, sa, sb, tc, ta, tb, tnabl;
	private ModelTable model;
	private ModelTableResult modelResult;
	private final String[] FILTERS = { "csv", "Excel (*.csv)" };
	private JLabel Label2;
	private JButton Button1;
	private JButton Button2;
	private JButton Button3;
	private JScrollPane js;
	private JComboBox comboBox;
	private JLabel lblNewLabel;
	private JPanel panel_2;
	private JTable table_1;
	private ArrayList<Double> arrx;
	private ArrayList<String> names;
	private ArrayList<String> values;
	private ArrayList<Double> arry_solve;
	private JLabel Labela,LabelAutor2;
	private JLabel Labelb, LabelAutor;
	private DoubleEditor tie;

	public Designer() throws IOException {
		xy = new XY();
		
		p = new ParseCSV();
		p.parseStud();
		model = new ModelTable(xy);

		modelResult = new ModelTableResult();
		names = new ArrayList<String>();
		values = new ArrayList<String>();
		initialize();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 596, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RefineryUtilities.centerFrameOnScreen(frame);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		table = new JTable();
	
		table.setBounds(91, 32, 108, 320);
		panel.add(table);
		table.setVisible(false);		
		
		
		DefaultCellEditor editor = (DefaultCellEditor)table.getDefaultEditor(Double.class);
        JComponent border = (JComponent)editor.getComponent();
        border.setBorder( BorderFactory.createLineBorder(Color.red));
		
		js = new JScrollPane(table);
		js.setVisible(false);
		js.setBounds(10, 11, 164, 343);
       
		panel.add(js);
      

		Label2 = new JLabel("Выберите функцию:");
		Label2.setBounds(184, 21, 170, 14);
		Label2.setVisible(false);
		panel.add(Label2);

		Button1 = new JButton("Посторить график");
		Button1.setBounds(184, 365, 386, 23);
		Button1.setVisible(false);
		Button1.addActionListener(new ActionListener() {///////////////// create graphic

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ShowGraph sg = new ShowGraph("График", ((ModelTable) table.getModel()).getXY(), n);
				sg.create("graphic", title, model.getXY());
				sg.pack();
				RefineryUtilities.centerFrameOnScreen(sg);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						sg.setVisible(true);

					}
				});
			}
		});

		panel.add(Button1);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"y = a + b * x", "y = a * exp(b * x)\t\t", "y = b * ln(x) + a"}));
		comboBox.setSelectedIndex(0);
		comboBox.setVisible(false);
		comboBox.setBounds(184, 46, 386, 20);
		comboBox.addActionListener(new ActionListener() {///////////////// choose function

			@Override
			public void actionPerformed(ActionEvent arg0) {
				n = comboBox.getSelectedIndex();
				title = comboBox.getSelectedItem().toString();
				check(n);
				Button1.setEnabled(true);
				Button2.setEnabled(true);
				Button3.setEnabled(true);
				addTableResult(names, values);
				table_1.setVisible(true);
				Labela.setText(xy.checkResultA(Double.valueOf(ta), Double.valueOf(tc)));				
				Labelb.setText(xy.checkResultB(Double.valueOf(tb), Double.valueOf(tc)));
			}
		});
		panel.add(comboBox);

		Button2 = new JButton("Взять текущие A и B и пересчитать");
		Button2.setVisible(false);
		Button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAB();

				arrx = xy.getX();
				arry_solve = new ArrayList<Double>();
				yx = 0;
				System.out.println(arrx.size());
				if (n == 0) {
					for (int i = 0; i < arrx.size(); i++) {
						yx = math(ax + bx * arrx.get(i));
						arry_solve.add(i, yx);
						// System.out.println(arry_solve.get(i));
					}
				} else if (n == 1) {
					for (int i = 0; i < arrx.size(); i++) {
						yx = math(ax * Math.exp(bx * arrx.get(i)));
						arry_solve.add(i, yx);
						// System.out.println(arry_solve.get(i));
					}
				} else if (n == 2) {
					for (int i = 0; i < arrx.size(); i++) {
						yx = math(ax * Math.log(arrx.get(i)) + bx);
						arry_solve.add(i, yx);
						// System.out.println(arry_solve.get(i));
					}
				}
				// xy.setY(arry_solve);
				xy.setY_solve(arry_solve);
				table.setModel(new ModelTable(xy));
				table.getColumnModel().getColumn(0).setCellEditor(new DoubleEditor(1,1000));
				table.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor(1,1000));	
			}
		});
		Button2.setBounds(184, 331, 386, 23);
		panel.add(Button2);

		Button3 = new JButton("Авто-заполнение");
		Button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				xy.setXYr(xy.getX().size());
				table.setModel(new ModelTable(xy));
				table.setVisible(true);
				Button1.setEnabled(false);
				Button2.setEnabled(false);
				Label2.setVisible(true);
				comboBox.setVisible(true);
				table_1.setModel(new ModelTableResult(names, modelResult.setYnull(values)));
				setSizeCol();
			}
		});
		Button3.setBounds(10, 365, 164, 23);
		Button3.setVisible(false);
		panel.add(Button3);

		panel_2 = new JPanel();
		panel_2.setBounds(184, 112, 386, 183);
		panel.add(panel_2);
		panel_2.setLayout(null);

		table_1 = new JTable();
		table_1.setBorder(new LineBorder(new Color(128, 128, 128)));
		table_1.setBounds(0, 11, 386, 161);
		panel_2.add(table_1);
		table_1.setBackground(new Color(240, 240, 240));

		Labela = new JLabel("");
		Labela.setBounds(184, 295, 386, 14);
		panel.add(Labela);

		Labelb = new JLabel("");
		Labelb.setBounds(184, 310, 386, 14);
		panel.add(Labelb);
		table_1.setVisible(false);

		// getColumnModel().getColumn(1).setWidth(150);
		lblNewLabel = new JLabel("Sa");
		LabelAutor2 = new JLabel("                      ");
		LabelAutor = new JLabel("Марчук | Осиновский");
		
		JMenuBar bar = new JMenuBar();
		JButton open = new JButton("Открыть файл");
		open.addActionListener(new ActionListener() {///////////////// OPEN

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					open();
					
					table.setModel(new ModelTable(xy));
					table.getColumnModel().getColumn(0).setCellEditor(new DoubleEditor(1,1000));
					table.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor(1,1000));					
					table.setVisible(true);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton save = new JButton("Сохранить как...");
		save.addActionListener(new ActionListener() {///////////////// SAVE

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton man = new JButton("Ввести вручную");
		man.addActionListener(new ActionListener() {///////////////// manual enter

			@Override
			public void actionPerformed(ActionEvent arg0) {

				num = showWindowMessage();
				System.out.print(num);
				xy = new XY(num);
				table.setModel(new ModelTable(xy));
				table.getColumnModel().getColumn(0).setCellEditor(new DoubleEditor(1,1000));
				table.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor(1,1000));					
				table.setVisible(true);
				Button1.setEnabled(false);
				Button2.setEnabled(false);
				Button3.setEnabled(true);
				Button1.setVisible(true);
				Button2.setVisible(true);
				Button3.setVisible(true);
				js.setVisible(true);
			}
		});

		bar.add(open);
		bar.add(save);
		bar.add(man);
		bar.add(LabelAutor2);
		bar.add(LabelAutor);

		frame.setTitle("Корреляционно-регрессионный анализ");
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}

	public Integer showWindowMessage() {
		JOptionPane jp = new JOptionPane();
		String s = "Введите кол-во точек (от 3 до 100):";
		String num2 = "";
		Integer result = 0;

		try {
			num2 = jp.showInputDialog(s);
			result = Integer.valueOf(num2);
			if (result <= 2 || result > 100) {
				throw new Exception();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Ошибка ввода!!!", "Ошибка", JOptionPane.ERROR_MESSAGE);
			System.out.println("error");
			result = showWindowMessage();
		}
		return result;
	}

	public void open() throws IOException {
		try {
			fileChooser.setDialogTitle("Открыть");			
			String extension = "";
			FileFilterMain eff = new FileFilterMain(FILTERS[0], FILTERS[1]);
			fileChooser.addChoosableFileFilter(eff);
			int result = fileChooser.showOpenDialog(Designer.this);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				extension = getFileExtension(file);
				System.out.println(extension);
				if (extension.equals("csv")) {
					p.setPath(file.getPath());
					p.parse(xy);
					xy.setY_solve_null();
					setVisible();
					Button1.setEnabled(false);
					Button2.setEnabled(false);
					Button3.setEnabled(false);
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {			
			JOptionPane.showMessageDialog(new JFrame(), "Выберите файл с расширением .csv!!!", "Ошибка", JOptionPane.ERROR_MESSAGE);
			open();
		}
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		// если в имени файла есть точка и она не является первым символом в названии
		// файла
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			// то вырезаем все знаки после последней точки в названии файла, то есть
			// ХХХХХ.txt -> txt
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		// в противном случае возвращаем заглушку, то есть расширение не найдено
		else
			return "";
	}

	public void save() throws IOException {
		fileChooser.setDialogTitle("Сохранить как...");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(Designer.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(Designer.this, "Файл " + fileChooser.getSelectedFile() + " сохранен");
			p.writeToFile(xy, fileChooser.getCurrentDirectory().toString(), fileChooser.getSelectedFile().getName());
		}
	}

	public void check(int n) {
		xy = ((ModelTable) table.getModel()).getXY();
		tc = String.valueOf(math(ParseCSV.student.get(xy.colX() - 2)));
		switch (n) {

		case 0:
			xy.calculateB();
			xy.calculateA();
			xy.calclLineY();
			System.out.println(n);
			cor = String.valueOf(math(xy.correlation()));
			det = String.valueOf(math(xy.R2()));
			a = String.valueOf(math(xy.getA()));
			b = String.valueOf(math(xy.getB()));
			sa = String.valueOf(math(xy.sa_line()));
			sb = String.valueOf(math(xy.sb_line()));
			ta = String.valueOf(math(xy.ta(Double.valueOf(a), xy.sa_line())));
			tb = String.valueOf(math(xy.tb(Double.valueOf(b), xy.sb_line())));
			tnabl = String.valueOf(math(xy.tnabl(xy.correlation())));
			break;
		case 1:
			xy.calculateA1();
			xy.calculateA0();
			xy.calclExpY();
			System.out.println(n);
			cor = String.valueOf(math(xy.correlation()));
			det = String.valueOf(math(xy.R2()));
			a = String.valueOf(math(xy.getA0()));
			b = String.valueOf(math(xy.getA1()));
			sa = String.valueOf(math(xy.sa_exp()));
			sb = String.valueOf(math(xy.sb_exp()));
			ta = String.valueOf(math(xy.ta(Double.valueOf(a), xy.sa_exp())));
			tb = String.valueOf(math(xy.tb(Double.valueOf(b), xy.sb_exp())));
			tnabl = String.valueOf(math(xy.tnabl(xy.correlation())));
			break;
		case 2:
			xy.calculatePl_A();
			xy.calculatePl_B();
			xy.calclLnY();
			System.out.println(n);
			cor = String.valueOf(math(xy.correlation_pln()));
			det = String.valueOf(math(xy.R2()));
			a = String.valueOf(math(xy.getPl_a()));
			b = String.valueOf(math(xy.getPl_b()));
			sa = String.valueOf(math(xy.sa_pln()));
			sb = String.valueOf(math(xy.sb_pln()));
			ta = String.valueOf(math(xy.ta(Double.valueOf(a), xy.sa_pln())));
			tb = String.valueOf(math(xy.tb(Double.valueOf(b), xy.sb_pln())));
			tnabl = String.valueOf(math(xy.tnabl(xy.correlation_pln())));
			break;

		default:
			break;
		}
		table.setModel(new ModelTable(xy));
		table.getColumnModel().getColumn(0).setCellEditor(new DoubleEditor(1,1000));
		table.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor(1,1000));	
		table_1.setModel(new ModelTableResult(names, values));
		setSizeCol();
	}

	public void setSizeCol() {
		table_1.getColumnModel().getColumn(0).setPreferredWidth(305);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(50);
	}

	public void setVisible() {
		Label2.setVisible(true);
		Button1.setVisible(true);
		Button2.setVisible(true);
		Button3.setVisible(true);
		js.setVisible(true);
		comboBox.setVisible(true);
	}

	public double math(double x) {
		return Math.ceil(x * 1000) / 1000;
	}

	public void saveAB() {
		ax = Double.valueOf(a);
		bx = Double.valueOf(b);
	}

	public void addTableResult(ArrayList<String> names, ArrayList<String> values) {
		names.clear();
		values.clear();
		names.add("коэффициент корреляции (r)");
		values.add(cor);
		names.add("коэффициент детерминации (R2)");
		values.add(det);
		names.add("коэффициент А (a)");
		values.add(a);
		names.add("коэффициент B (b)");
		values.add(b);
		names.add("стандартное отклонение случайной величины a (Sa)");
		values.add(sa);
		names.add("стандартное отклонение случайной величины b (Sb)");
		values.add(sb);
		names.add("коэффициента регрессии a (t_a)");
		values.add(ta);
		names.add("коэффициента регрессии b (t_b)");
		values.add(tb);
		names.add("табличное значение t-критерия (t_c)");
		values.add(tc);
		names.add("наблюдаемое значение критерия (t_nabl)");
		values.add(tnabl);
	}
	
	public double validTable(JTable table) {
		int rowIndex = table.getSelectedRow();
		int colIndex = table.getSelectedColumn();
		Double value =(Double) table.getValueAt(rowIndex, colIndex);
		return value;
	}
	

}
