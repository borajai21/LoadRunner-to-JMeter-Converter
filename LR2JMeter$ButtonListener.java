import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

class LR2JMeter$ButtonListener
  implements ActionListener
{
  LR2JMeter$ButtonListener(LR2JMeter paramLR2JMeter) {}
  
  public void actionPerformed(ActionEvent ae)
  {
    if (!LR2JMeter.access$3(this.this$0).equals(""))
    {
      this.this$0.save.setEnabled(false);
      this.this$0.i = 0;
      String str = "<html><font color=\"#FF0000\"><b>Conversion is in process.......</b></font></html>";
      
      this.this$0.label.setText(str);
      FileMatch jE = new FileMatch();
      jE.inputFile = LR2JMeter.access$3(this.this$0);
      try
      {
        LR2JMeter.access$6(this.this$0, jE.convertFile(jE.inputFile, LR2JMeter.access$4(this.this$0), LR2JMeter.access$5(this.this$0)));
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      this.this$0.timer.start();
      LR2JMeter.access$0(this.this$0, "");
    }
    else
    {
      this.this$0.label.setText("Please Select a File by Clicking on the Browse button");
    }
  }
}
