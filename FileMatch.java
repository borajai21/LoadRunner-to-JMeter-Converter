import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Element;

public class FileMatch
{
  public String inputFile = null;
  public String outputFile = null;
  String result = "";
  
  public static void main(String[] args)
  {
    System.out.println("All Done..");
  }
  
  public String convertFile(String inputfile, String output, String oname)
    throws IOException
  {
    String test = readFile(inputfile);
    test = test.replace("\r\n", " ").replace("\n", " ");
    
    int reqcount = 0;
    
    String[] temp = test.split(";");
    
    XMLCreator xce = new XMLCreator();
    Element hashTree = xce.createDOMTree();
    for (int i = 0; i < temp.length; i++)
    {
      String weborhttp = "null";
      String isweb = "null";
      if (temp[i].contains("web_custom_request"))
      {
        weborhttp = "HTTP";
        for (int j = i - 1; j >= 0; j--)
        {
          if (temp[j].contains("web_add_header"))
          {
            weborhttp = patternMatch(temp[j], "(?<=web_add_header\\().*.(?=\\))");
            isweb = "WEBS";
          }
          if ((temp[j].contains("web_url")) || (temp[j].contains("web_submit_data")) || (temp[j].contains("web_custom_request")) || (temp[j].contains("web_service_call")) || (temp[j].contains("soap_request"))) {
            break;
          }
        }
      }
      if ((temp[i].contains("web_submit_data")) || (temp[i].contains("web_url")) || (weborhttp.equals("HTTP")))
      {
        URL aURL = null;
        String[] manurl = (String[])null;
        String[] weburl = (String[])null;
        String[] webrsp = (String[])null;
        String[] webrf = (String[])null;
        String[] newURL = (String[])null;
        String lrtt = "null";
        String res = null;
        String lb = null;
        String rb = null;
        String ord = null;
        String port = "";
        List regName = new ArrayList();
        List regX = new ArrayList();
        List regOrd = new ArrayList();
        List asNam = new ArrayList();
        List namelist = new ArrayList();
        List valuelist = new ArrayList();
        List webreg = new ArrayList();
        List webfind = new ArrayList();
        List thinktime = new ArrayList();
        String trnx = "TransactionController";
        if (temp[i].contains("web_url")) {
          res = patternMatch(temp[i], "(?<=web_url\\().*.(?=\\))");
        }
        if (temp[i].contains("web_submit_data")) {
          res = patternMatch(temp[i], "(?<=web_submit_data\\().*.(?=\\))");
        }
        if (temp[i].contains("web_custom_request")) {
          res = patternMatch(temp[i], "(?<=web_custom_request\\().*.(?=\\))");
        }
        if (res != null)
        {
          weburl = res.split(",");
          if ((temp[i].contains("web_url")) || (temp[i].contains("web_custom_request"))) {
            newURL = weburl[1].split("URL=");
          }
          if (temp[i].contains("web_submit_data")) {
            newURL = weburl[1].split("Action=");
          }
          if ((newURL[1].startsWith("http")) || (newURL[1].startsWith("https"))) {
            aURL = new URL(newURL[1]);
          } else {
            manurl = newURL[1].split("://");
          }
          for (int j = i - 1; j >= 0; j--)
          {
            if (temp[j].contains("lr_start_transaction")) {
              trnx = patternMatch(temp[j], "(?<=lr_start_transaction\\().*.(?=\\))");
            }
            if (temp[j].contains("web_reg_save_param"))
            {
              webrsp = patternMatch(temp[j], "(?<=web_reg_save_param\\().*.(?=\\))").split(",");
              webreg.add(webrsp);
            }
            if (temp[j].contains("web_reg_find"))
            {
              webrf = patternMatch(temp[j], "(?<=web_reg_find\\().*.(?=\\))").split(",");
              webfind.add(webrf);
            }
            if (temp[j].contains("lr_think_time"))
            {
              lrtt = patternMatch(temp[j], "(?<=lr_think_time\\().*.(?=\\))");
              thinktime.add(lrtt);
            }
            if ((temp[j].contains("web_url")) || (temp[j].contains("web_submit_data")) || (temp[j].contains("web_custom_request")) || (temp[j].contains("web_service_call")) || (temp[j].contains("soap_request"))) {
              break;
            }
          }
          if (temp[i].contains("web_submit_data")) {
            for (int k = 0; k < weburl.length; k++)
            {
              if (weburl[k].contains("Name="))
              {
                String[] name = weburl[k].split("Name=");
                namelist.add(name[1]);
              }
              if (weburl[k].contains("Value="))
              {
                String[] value = weburl[k].split("Value=");
                if (value.length == 1) {
                  valuelist.add(" ");
                } else {
                  valuelist.add(value[1]);
                }
              }
            }
          }
          if (!trnx.equals(null)) {
            trnx = trnx + "_" + weburl[0];
          } else {
            trnx = trnx + "_" + weburl[0];
          }
          for (int z = 0; z < webreg.size(); z++)
          {
            String[] webrspdata = (String[])webreg.get(z);
            regName.add(webrspdata[0]);
            ord = null;
            for (int l = 0; l < webrspdata.length; l++)
            {
              webrspdata[l] = webrspdata[l].trim();
              if (webrspdata[l].startsWith("LB=")) {
                lb = webrspdata[l].substring(3);
              }
              if (webrspdata[l].startsWith("RB=")) {
                rb = webrspdata[l].substring(3);
              }
              if (webrspdata[l].startsWith("ORD=")) {
                ord = webrspdata[l].substring(4);
              }
              if ((lb != null) && (rb != null)) {
                regX.add(lb + "(.*?)" + rb);
              }
            }
            if (ord != null) {
              regOrd.add(ord);
            }
            if (ord == null) {
              regOrd.add("1");
            }
          }
          for (int z = 0; z < webfind.size(); z++)
          {
            String[] webrspdata = (String[])webfind.get(z);
            for (int l = 0; l < webrspdata.length; l++)
            {
              webrspdata[l] = webrspdata[l].trim();
              if (webrspdata[l].startsWith("Text=")) {
                asNam.add(webrspdata[l].substring(5));
              }
              if (webrspdata[l].startsWith("Text/IC=")) {
                asNam.add(webrspdata[l].substring(8));
              }
            }
          }
          if ((newURL[1].startsWith("http")) || (newURL[1].startsWith("https")))
          {
            if (aURL.getPort() != -1) {
              port = String.valueOf(aURL.getPort());
            }
            xce.addTC(hashTree, trnx, namelist, valuelist, aURL.getProtocol().toString(), aURL.getHost().toString(), port, aURL.getFile().toString(), regName, regX, regOrd, asNam, thinktime);
          }
          else
          {
            xce.addTC(hashTree, trnx, namelist, valuelist, "$" + manurl[0].toString(), "${host}", "${port}", manurl[1].substring(6, manurl[1].length()), regName, regX, regOrd, asNam, thinktime);
          }
        }
      }
      if ((temp[i].contains("web_service_call")) || (temp[i].contains("soap_request")) || (isweb.equals("WEBS")))
      {
        String res = null;
        String wsurl = "URL not found";
        String wssoapaction = "SOAP Action not found";
        String[] weburl = (String[])null;
        String[] newURL = (String[])null;
        String[] wshead = (String[])null;
        String[] wscrn = (String[])null;
        String lrtt = "null";
        String lxgv = null;
        String lxf = null;
        List thinktime = new ArrayList();
        List xmlgetvalues = new ArrayList();
        List xmlfind = new ArrayList();
        List xfind = new ArrayList();
        List xpathvalue = new ArrayList();
        reqcount++;
        if (temp[i].contains("web_service_call")) {
          res = patternMatch(temp[i], "(?<=web_service_call\\().*.(?=\\))");
        }
        if (temp[i].contains("soap_request")) {
          res = patternMatch(temp[i], "(?<=soap_request\\().*.(?=\\))");
        }
        if (temp[i].contains("web_custom_request")) {
          res = patternMatch(temp[i], "(?<=web_custom_request\\().*.(?=\\))");
        }
        if (res != null)
        {
          weburl = res.split(",");
          if (temp[i].contains("web_custom_request")) {
            newURL = weborhttp.split(",");
          }
          if ((temp[i].contains("web_service_call")) || (temp[i].contains("soap_request"))) {
            newURL = weburl[0].split("StepName=");
          }
          String wsrbody = readFile(output + "\\result1\\Iteration1\\t" + reqcount + "_RequestBody.txt");
          String wsrhead = readFile(output + "\\result1\\Iteration1\\t" + reqcount + "_RequestHeader.txt");
          
          wsrhead = wsrhead.replace("\r\n", " ").replace("\n", " ");
          wshead = wsrhead.split(" ");
          
          weborhttp = weborhttp.replace("\r\n", " ").replace("\n", " ");
          wscrn = weborhttp.split(",");
          for (int z = 0; z < wshead.length; z++)
          {
            if (wshead[z].equals("POST")) {
              wsurl = wshead[(z + 1)];
            }
            if (wshead[z].equals("SOAPAction:")) {
              wssoapaction = wshead[(z + 1)];
            }
          }
          wsurl = wsurl.replace("\"", "");
          wssoapaction = wssoapaction.replace("\"", "");
          for (int j = i - 1; j >= 0; j--)
          {
            if (temp[j].contains("lr_think_time"))
            {
              lrtt = patternMatch(temp[j], "(?<=lr_think_time\\().*.(?=\\))");
              thinktime.add(lrtt);
            }
            if ((temp[j].contains("web_url")) || (temp[j].contains("web_submit_data")) || (temp[j].contains("web_custom_request")) || (temp[j].contains("web_service_call")) || (temp[j].contains("soap_request"))) {
              break;
            }
          }
          for (int j = i + 1; j < temp.length; j++)
          {
            if (temp[j].contains("lr_xml_get_values"))
            {
              lxgv = patternMatch(temp[j], "(?<=lr_xml_get_values\\().*.(?=\\))");
              xmlgetvalues.add(lxgv);
            }
            if (temp[j].contains("lr_xml_find"))
            {
              lxf = patternMatch(temp[j], "(?<=lr_xml_find\\().*.(?=\\))");
              xmlfind.add(lxf);
            }
            if ((temp[j].contains("web_url")) || (temp[j].contains("web_submit_data")) || (temp[j].contains("web_custom_request")) || (temp[j].contains("web_service_call")) || (temp[j].contains("soap_request"))) {
              break;
            }
          }
          for (int y = 0; y < xmlfind.size(); y++)
          {
            String[] webrspdata = xmlfind.get(y).toString().split(",");
            for (int l = 0; l < webrspdata.length; l++)
            {
              webrspdata[l] = webrspdata[l].trim();
              if (webrspdata[l].startsWith("Value=")) {
                xfind.add(webrspdata[l].substring(6));
              }
            }
          }
          for (int z = 0; z < xmlgetvalues.size(); z++)
          {
            String[] webrspdata = xmlgetvalues.get(z).toString().split(",");
            for (int l = 0; l < webrspdata.length; l++)
            {
              webrspdata[l] = webrspdata[l].trim();
              if (webrspdata[l].startsWith("Query=")) {
                xpathvalue.add(webrspdata[l].substring(6));
              }
            }
          }
          xce.addWebTC(hashTree, newURL[1], wsurl, wsrbody, wssoapaction, xfind, thinktime, xpathvalue);
        }
      }
    }
    xce.printToFile(output, oname);
    this.result = "Conversion to JMX file completed";
    return this.result;
  }
  
  private static String readFile(String file)
    throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = null;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = System.getProperty("line.separator");
    while ((line = reader.readLine()) != null)
    {
      line = line.replaceAll("(^\")|(\"$)", "");
      stringBuilder.append(line);
      stringBuilder.append(ls);
    }
    return stringBuilder.toString();
  }
  
  private static String patternMatch(String toMatch, String patt)
  {
    String result = null;
    Pattern pattern = Pattern.compile(patt);
    Matcher matcher = pattern.matcher(toMatch);
    
    boolean found = false;
    while (matcher.find())
    {
      result = matcher.group().toString().replaceAll("[\"]", "");
      found = true;
    }
    return result;
  }
}
