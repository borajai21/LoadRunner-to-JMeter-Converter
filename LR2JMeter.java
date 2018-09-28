import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LR2JMeter
{
  static final int interval = 20;
  int i;
  JLabel label;
  JProgressBar pb;
  Timer timer;
  JButton save;
  JButton open;
  private String path;
  private String outputname;
  private String outputfile;
  private String finalmess;
  
  public LR2JMeter()
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (UnsupportedLookAndFeelException e)
    {
      e.printStackTrace();
    }
    JFrame frame = new JFrame("LR-to-JMeter v2.1");
    this.save = new JButton("Convert");
    this.save.addActionListener(new LR2JMeter.ButtonListener());
    this.save.setEnabled(false);
    
    this.open = new JButton("Browse");
    this.open.addActionListener(new LR2JMeter.OpenL());
    
    this.pb = new JProgressBar(0, 20);
    this.pb.setValue(0);
    this.pb.setStringPainted(true);
    
    this.label = new JLabel("<html><font color=\"#0000A0\"><b>Click Browse and locate the Load Runner C file</b></font></html>");
    
    JPanel panel = new JPanel();
    panel.add(this.open);
    panel.add(this.save);
    
    JPanel progress = new JPanel();
    progress.add(this.pb);
    
    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.add(panel, "Center");
    panel1.add(progress, "North");
    panel1.add(this.label, "Last");
    panel1.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(1), "Load Runner to JMeter"));
    
    JMenuBar menubar = new JMenuBar();
    JMenu helpmenu = new JMenu("Help");
    menubar.add(Box.createHorizontalGlue());
    menubar.add(helpmenu);
    
    JMenuItem helpitem = new JMenuItem("Help");
    
    helpitem.setAccelerator(KeyStroke.getKeyStroke("control H"));
    helpitem.addActionListener(new LR2JMeter.MenuActionListener());
    JMenuItem aboutitem = new JMenuItem("About");
    aboutitem.setAccelerator(KeyStroke.getKeyStroke("control A"));
    aboutitem.addActionListener(new LR2JMeter.MenuActionListener());
    helpmenu.add(helpitem);
    helpmenu.add(aboutitem);
    frame.setJMenuBar(menubar);
    
    frame.setContentPane(panel1);
    frame.pack();
    frame.setBounds(300, 200, 250, 175);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(3);
    
    this.timer = new Timer(20, new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        if (LR2JMeter.this.i == 20)
        {
          Toolkit.getDefaultToolkit().beep();
          LR2JMeter.this.timer.stop();
          
          LR2JMeter.this.pb.setValue(0);
          String str = "<html><font color=\"#008000\"><b>" + 
            LR2JMeter.this.finalmess + " at " + LR2JMeter.this.outputfile + "</b>" + "</font>" + "</html> ";
          LR2JMeter.this.label.setText(str);
        }
        LR2JMeter.this.i += 1;
        LR2JMeter.this.pb.setValue(LR2JMeter.this.i);
      }
    });
  }
  
  class OpenL
    implements ActionListener
  {
    OpenL() {}
    
    public void actionPerformed(ActionEvent e)
    {
      JFileChooser fileChooser = new JFileChooser(".");
      int status = fileChooser.showOpenDialog(null);
      if (status == 0)
      {
        File selectedFile = fileChooser.getSelectedFile();
        StringTokenizer st = new StringTokenizer(fileChooser.getSelectedFile().getName(), ".");
        LR2JMeter.this.path = (fileChooser.getCurrentDirectory().toString() + "\\" + fileChooser.getSelectedFile().getName());
        LR2JMeter.this.outputfile = fileChooser.getCurrentDirectory().toString();
        LR2JMeter.this.outputname = fileChooser.getSelectedFile().getName();
        String nkey = st.nextToken();
        String ekey = st.nextToken();
        if (ekey.startsWith("c"))
        {
          LR2JMeter.this.label.setText("<html><font color=\"#808000\"><b>C File is now loaded, Kindly click on \"Convert\" to start processing. </b></font></html>");
          
          LR2JMeter.this.save.setEnabled(true);
          LR2JMeter.this.pb.setValue(0);
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
  
  class ButtonListener
    implements ActionListener
  {
    ButtonListener() {}
    
    public void actionPerformed(ActionEvent ae)
    {
      if (!LR2JMeter.this.path.equals(""))
      {
        LR2JMeter.this.save.setEnabled(false);
        LR2JMeter.this.i = 0;
        String str = "<html><font color=\"#FF0000\"><b>Conversion is in process.......</b></font></html>";
        
        LR2JMeter.this.label.setText(str);
        FileMatch jE = new FileMatch();
        jE.inputFile = LR2JMeter.this.path;
        try
        {
          LR2JMeter.this.finalmess = jE.convertFile(jE.inputFile, LR2JMeter.this.outputfile, LR2JMeter.this.outputname);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        LR2JMeter.this.timer.start();
        LR2JMeter.this.path = "";
      }
      else
      {
        LR2JMeter.this.label.setText("Please Select a File by Clicking on the Browse button");
      }
    }
  }
  
  class MenuActionListener
    implements ActionListener
  {
    MenuActionListener() {}
    
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
        JOptionPane.showMessageDialog(null, "Load Runner to JMeter v2.1 \n Developed by Karan Sabharwal", "ABOUT", 1);
      }
    }
  }
  
  public static void main(String[] args)
  {
    LR2JMeter spb = new LR2JMeter();
  }
}
