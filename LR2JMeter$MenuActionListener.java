import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;

class LR2JMeter$MenuActionListener
  implements ActionListener
{
  LR2JMeter$MenuActionListener(LR2JMeter paramLR2JMeter) {}
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().toString().equals("Help")) {
      try
      {
        Runtime.getRuntime().exec("hh.exe Help.mht");
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    if (e.getActionCommand().toString().equals("About")) {
      JOptionPane.showMessageDialog(null, "Load Runner to JMeter v2.1 \n Developed by Dell NFT COE Team", "ABOUT", 1);
    }
  }
}
