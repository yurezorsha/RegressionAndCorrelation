import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;


public class DoubleEditor extends DefaultCellEditor
    {
        JFormattedTextField ftf;
        NumberFormat doubleFormat;
        private Double minimum, maximum;
        private boolean DEBUG = false;

        public DoubleEditor(double min)
        {
            super(new JFormattedTextField());

            setClickCountToStart(2);

            ftf = (JFormattedTextField)getComponent();
            ftf.setBorder(new LineBorder(Color.BLACK));
            minimum = new Double(min);
            //maximum = new Double(max);

            
            doubleFormat = NumberFormat.getInstance();
            NumberFormatter intFormatter = new NumberFormatter(doubleFormat);
            intFormatter.setFormat(doubleFormat);
            intFormatter.setMinimum(minimum);
            //intFormatter.setMaximum(maximum);

            ftf.setFormatterFactory(new DefaultFormatterFactory(intFormatter));
            ftf.setValue(minimum);
           // ftf.setHorizontalAlignment(JTextField.TRAILING);
            ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);

      
            ftf.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");

            ftf.getActionMap().put("check", new AbstractAction()
            {
                @Override
				public void actionPerformed(ActionEvent e)
                {
                    if (!ftf.isEditValid())  //The text is invalid.
                    {
                        if (userSaysRevert())
                        {
                            ftf.postActionEvent(); //inform the editor
                        }
                    }
                    else
                        try
                        {
                            ftf.commitEdit();     //so use it.
                            ftf.postActionEvent(); //stop editing
                        }
                        catch (java.text.ParseException exc) { }
                }
            });
        }

        @Override
        public boolean isCellEditable(EventObject event)
        {
            JTable table = (JTable)event.getSource();
            return true;
        }

  
        @Override
		public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
        {
             JFormattedTextField ftf = (JFormattedTextField)super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
             ftf.setValue(value);

             return ftf;
        }

    
        @Override
		public Object getCellEditorValue()
        {
            JFormattedTextField ftf = (JFormattedTextField)getComponent();
            Object o = ftf.getValue();
            if (o instanceof Double)
            {
                return o;
            }
            else if (o instanceof Number)
            {
                return new Double(((Number)o).doubleValue());
            }
            else
            {
                try
                {
                    return doubleFormat.parseObject(o.toString());
                }
                catch (ParseException exc)
                {
                    System.err.println("getCellEditorValue: can't parse o: " + o);
                    return null;
                }
            }
        }


        @Override
		public boolean stopCellEditing()
        {
            JFormattedTextField ftf = (JFormattedTextField)getComponent();

            if (ftf.isEditValid())
            {
                try
                {
                    ftf.commitEdit();
                }
                catch (java.text.ParseException exc) { }

            }
            else
            {
                if (!userSaysRevert())  //user wants to edit
                {
                    return false; //don't let the editor go away
                }
             }

             return super.stopCellEditing();
        }

      
        protected boolean userSaysRevert() {
             Toolkit.getDefaultToolkit().beep();
             ftf.selectAll();
             Object[] options = {"Изменить",
                                        "Отменить"};
             int answer = JOptionPane.showOptionDialog(
                  SwingUtilities.getWindowAncestor(ftf),
                  "Значение может быть только больше 0"                  
                  +  ".\n"
                  + "Вы можете изменить значение "
                  + "или вернуть предыдущее значение",
                  "Ошибка ввода",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.ERROR_MESSAGE,
                  null,
                  options,
                  options[1]);

             if (answer == 1) { //Revert!
                  ftf.setValue(ftf.getValue());
             return true;
             }
        return false;
        }

}
