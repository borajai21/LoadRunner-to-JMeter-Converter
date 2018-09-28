import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

class LR2JMeter$OpenL
  implements ActionListener
{
  LR2JMeter$OpenL(LR2JMeter paramLR2JMeter) {}
  
  public void actionPerformed(ActionEvent e)
  {
    JFileChooser fileChooser = new JFileChooser(".");
    int status = fileChooser.showOpenDialog(null);
    if (status == 0)
    {
      File selectedFile = fileChooser.getSelectedFile();
      StringTokenizer st = new StringTokenizer(fileChooser.getSelectedFile().getName(), ".");
      LR2JMeter.access$0(this.this$0, fileChooser.getCurrentDirectory().toString() + "\\" + fileChooser.getSelectedFile().getName());
      LR2JMeter.access$1(this.this$0, fileChooser.getCurrentDirectory().toString());
      LR2JMeter.access$2(this.this$0, fileChooser.getSelectedFile().getName());
      String nkey = st.nextToken();
      String ekey = st.nextToken();
      if (ekey.startsWith("c"))
      {
        this.this$0.label.setText("<html><font color=\"#808000\"><b>C File is now loaded, Kindly click on \"Convert\" to start processing. </b></font></html>");
        
        this.this$0.save.setEnabled(true);
        this.this$0.pb.setValue(0);
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Format not supported - Try .c", "Error", -1);
      }
    }
    else if (status == 1)
    {
      System.out.println("canceled");
    }
  }
}
