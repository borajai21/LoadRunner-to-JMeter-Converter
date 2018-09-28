import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

class LR2JMeter$1
  implements ActionListener
{
  LR2JMeter$1(LR2JMeter paramLR2JMeter) {}
  
  public void actionPerformed(ActionEvent evt)
  {
    if (this.this$0.i == 20)
    {
      Toolkit.getDefaultToolkit().beep();
      this.this$0.timer.stop();
      
      this.this$0.pb.setValue(0);
      String str = "<html><font color=\"#008000\"><b>" + 
        LR2JMeter.access$7(this.this$0) + " at " + LR2JMeter.access$4(this.this$0) + "</b>" + "</font>" + "</html> ";
      this.this$0.label.setText(str);
    }
    this.this$0.i += 1;
    this.this$0.pb.setValue(this.this$0.i);
  }
}
