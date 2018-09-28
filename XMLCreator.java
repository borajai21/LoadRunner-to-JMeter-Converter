import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLCreator
{
  List myData;
  Document dom;
  
  public XMLCreator()
  {
    this.myData = new ArrayList();
    
    createDocument();
  }
  
  public void runExample()
  {
    System.out.println("Started .. ");
    createDOMTree();
    printToFile("c:\\", "test");
    System.out.println("Generated file successfully.");
  }
  
  private void createDocument()
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder db = dbf.newDocumentBuilder();
      
      this.dom = db.newDocument();
    }
    catch (ParserConfigurationException pce)
    {
      System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
      System.exit(1);
    }
  }
  
  public Element createDOMTree()
  {
    Element rootEle = this.dom.createElement("jmeterTestPlan");
    rootEle.setAttribute("version", "1.2");
    rootEle.setAttribute("properties", "2.1");
    this.dom.appendChild(rootEle);
    Element hashTreeEle = this.dom.createElement("hashTree");
    rootEle.appendChild(hashTreeEle);
    
    Element tpEle = this.dom.createElement("TestPlan");
    tpEle.setAttribute("guiclass", "TestPlanGui");
    tpEle.setAttribute("testclass", "TestPlan");
    tpEle.setAttribute("testname", "Test Plan");
    tpEle.setAttribute("enabled", "true");
    hashTreeEle.appendChild(tpEle);
    
    Element spEle = this.dom.createElement("stringProp");
    spEle.setAttribute("name", "TestPlan.comments");
    tpEle.appendChild(spEle);
    
    Element bpEle = this.dom.createElement("boolProp");
    bpEle.setAttribute("name", "TestPlan.functional_mode");
    Text bpText = this.dom.createTextNode("flase");
    bpEle.appendChild(bpText);
    tpEle.appendChild(bpEle);
    
    Element bpEle2 = this.dom.createElement("boolProp");
    bpEle2.setAttribute("name", "TestPlan.serialize_threadgroups");
    Text bpText2 = this.dom.createTextNode("flase");
    bpEle2.appendChild(bpText2);
    tpEle.appendChild(bpEle2);
    
    Element epEle = this.dom.createElement("elementProp");
    epEle.setAttribute("name", "TestPlan.user_defined_variables");
    epEle.setAttribute("elementType", "Arguments");
    epEle.setAttribute("guiclass", "ArgumentsPanel");
    epEle.setAttribute("testclass", "Arguments");
    epEle.setAttribute("testname", "User Defined Variables");
    epEle.setAttribute("enabled", "true");
    tpEle.appendChild(epEle);
    
    Element cpEle = this.dom.createElement("collectionProp");
    cpEle.setAttribute("name", "Arguments.arguments");
    epEle.appendChild(cpEle);
    
    Element spEle2 = this.dom.createElement("stringProp");
    spEle2.setAttribute("name", "TestPlan.user_define_classpath");
    tpEle.appendChild(spEle2);
    
    Element hashTreeEle2 = this.dom.createElement("hashTree");
    hashTreeEle.appendChild(hashTreeEle2);
    Element tcEle = createThreadGroup(hashTreeEle2);
    return tcEle;
  }
  
  public void printToFile(String outpath, String oname)
  {
    try
    {
      OutputFormat format = new OutputFormat(this.dom);
      format.setIndenting(true);
      
      String[] name = outpath.split("\\\\");
      int length = name.length - 1;
      
      XMLSerializer serializer = new XMLSerializer(
        new FileOutputStream(new File(outpath + "\\" + oname + "_" + "JMeterScript.jmx")), format);
      
      serializer.serialize(this.dom);
    }
    catch (IOException ie)
    {
      ie.printStackTrace();
    }
  }
  
  public Element createThreadGroup(Element hashTreeEle)
  {
    Element hashTreeEle2 = null;
    try
    {
      Element cmEle = this.dom.createElement("CookieManager");
      cmEle.setAttribute("guiclass", "CookiePanel");
      cmEle.setAttribute("testclass", "CookieManager");
      cmEle.setAttribute("testname", "HTTP Cookie Manager");
      cmEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(cmEle);
      
      Element copEle = this.dom.createElement("collectionProp");
      copEle.setAttribute("name", "CookieManager.cookies");
      cmEle.appendChild(copEle);
      
      Element bopEle = this.dom.createElement("boolProp");
      bopEle.setAttribute("name", "CookieManager.clearEachIteration");
      Text bopText = this.dom.createTextNode("false");
      bopEle.appendChild(bopText);
      cmEle.appendChild(bopEle);
      
      Element sopEle = this.dom.createElement("stringProp");
      sopEle.setAttribute("name", "CookieManager.policy");
      Text sopText = this.dom.createTextNode("continue");
      sopEle.appendChild(sopText);
      cmEle.appendChild(sopEle);
      
      Element htEle = this.dom.createElement("hashTree");
      hashTreeEle.appendChild(htEle);
      
      Element hmEle = this.dom.createElement("HeaderManager");
      hmEle.setAttribute("guiclass", "HeaderPanel");
      hmEle.setAttribute("testclass", "HeaderManager");
      hmEle.setAttribute("testname", "HTTP Header Manager");
      hmEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(hmEle);
      
      Element hepEle = this.dom.createElement("collectionProp");
      hepEle.setAttribute("name", "HeaderManager.headers");
      hmEle.appendChild(hepEle);
      
      Element hcopEle = this.dom.createElement("elementProp");
      hcopEle.setAttribute("name", "Accept-Language");
      hcopEle.setAttribute("elementType", "Header");
      hepEle.appendChild(hcopEle);
      
      Element hspEle = this.dom.createElement("stringProp");
      hspEle.setAttribute("name", "Header.name");
      Text hspText = this.dom.createTextNode("Accept-Language");
      hspEle.appendChild(hspText);
      hcopEle.appendChild(hspEle);
      
      Element hspEle2 = this.dom.createElement("stringProp");
      hspEle2.setAttribute("name", "Header.value");
      Text hspText2 = this.dom.createTextNode("en-us");
      hspEle2.appendChild(hspText2);
      hcopEle.appendChild(hspEle2);
      
      Element hcopEle2 = this.dom.createElement("elementProp");
      hcopEle2.setAttribute("name", "Accept");
      hcopEle2.setAttribute("elementType", "Header");
      hepEle.appendChild(hcopEle2);
      
      Element hspEle3 = this.dom.createElement("stringProp");
      hspEle3.setAttribute("name", "Header.name");
      Text hspText3 = this.dom.createTextNode("Accept");
      hspEle3.appendChild(hspText3);
      hcopEle2.appendChild(hspEle3);
      
      Element hspEle4 = this.dom.createElement("stringProp");
      hspEle4.setAttribute("name", "Header.value");
      Text hspText4 = this.dom.createTextNode("image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/msword, application/x-ms-application, application/x-ms-xbap, application/vnd.ms-xpsdocument, application/xaml+xml, application/vnd.ms-powerpoint, */*");
      hspEle4.appendChild(hspText4);
      hcopEle2.appendChild(hspEle4);
      
      Element hcopEle3 = this.dom.createElement("elementProp");
      hcopEle3.setAttribute("name", "User-Agent");
      hcopEle3.setAttribute("elementType", "Header");
      hepEle.appendChild(hcopEle3);
      
      Element hspEle5 = this.dom.createElement("stringProp");
      hspEle5.setAttribute("name", "Header.name");
      Text hspText5 = this.dom.createTextNode("User-Agent");
      hspEle5.appendChild(hspText5);
      hcopEle3.appendChild(hspEle5);
      
      Element hspEle6 = this.dom.createElement("stringProp");
      hspEle6.setAttribute("name", "Header.value");
      Text hspText6 = this.dom.createTextNode("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
      hspEle6.appendChild(hspText6);
      hcopEle3.appendChild(hspEle6);
      
      Element hcopEle4 = this.dom.createElement("elementProp");
      hcopEle4.setAttribute("name", "Accept-Encoding");
      hcopEle4.setAttribute("elementType", "Header");
      hepEle.appendChild(hcopEle4);
      
      Element hspEle7 = this.dom.createElement("stringProp");
      hspEle7.setAttribute("name", "Header.name");
      Text hspText7 = this.dom.createTextNode("Accept-Encoding");
      hspEle7.appendChild(hspText7);
      hcopEle4.appendChild(hspEle7);
      
      Element hspEle8 = this.dom.createElement("stringProp");
      hspEle8.setAttribute("name", "Header.value");
      Text hspText8 = this.dom.createTextNode("gzip, deflate");
      hspEle8.appendChild(hspText8);
      hcopEle4.appendChild(hspEle8);
      
      Element hcopEle5 = this.dom.createElement("elementProp");
      hcopEle5.setAttribute("name", "UA-CPU");
      hcopEle5.setAttribute("elementType", "Header");
      hepEle.appendChild(hcopEle5);
      
      Element hspEle9 = this.dom.createElement("stringProp");
      hspEle9.setAttribute("name", "Header.name");
      Text hspText9 = this.dom.createTextNode("UA-CPU");
      hspEle9.appendChild(hspText9);
      hcopEle5.appendChild(hspEle9);
      
      Element hspEle10 = this.dom.createElement("stringProp");
      hspEle10.setAttribute("name", "Header.value");
      Text hspText10 = this.dom.createTextNode("x86");
      hspEle10.appendChild(hspText10);
      hcopEle5.appendChild(hspEle10);
      
      Element htEle2 = this.dom.createElement("hashTree");
      hashTreeEle.appendChild(htEle2);
      
      Element tgEle = this.dom.createElement("ThreadGroup");
      tgEle.setAttribute("guiclass", "ThreadGroupGui");
      tgEle.setAttribute("testclass", "ThreadGroup");
      tgEle.setAttribute("testname", "Thread Group");
      tgEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(tgEle);
      
      Element spEle = this.dom.createElement("stringProp");
      spEle.setAttribute("name", "ThreadGroup.on_sample_error");
      Text spText = this.dom.createTextNode("continue");
      spEle.appendChild(spText);
      tgEle.appendChild(spEle);
      
      Element epEle = this.dom.createElement("elementProp");
      epEle.setAttribute("name", "ThreadGroup.main_controller");
      epEle.setAttribute("elementType", "LoopController");
      epEle.setAttribute("guiclass", "LoopControlPanel");
      epEle.setAttribute("testclass", "LoopController");
      epEle.setAttribute("testname", "Loop Controller");
      epEle.setAttribute("enabled", "true");
      tgEle.appendChild(epEle);
      
      Element bpEle = this.dom.createElement("boolProp");
      bpEle.setAttribute("name", "LoopController.continue_forever");
      Text bpText = this.dom.createTextNode("false");
      bpEle.appendChild(bpText);
      epEle.appendChild(bpEle);
      
      Element stpEle = this.dom.createElement("stringProp");
      stpEle.setAttribute("name", "LoopController.loops");
      Text stpText = this.dom.createTextNode("1");
      stpEle.appendChild(stpText);
      epEle.appendChild(stpEle);
      
      Element spEle2 = this.dom.createElement("stringProp");
      spEle2.setAttribute("name", "ThreadGroup.num_threads");
      Text spText2 = this.dom.createTextNode("1");
      spEle2.appendChild(spText2);
      tgEle.appendChild(spEle2);
      
      Element spEle3 = this.dom.createElement("stringProp");
      spEle3.setAttribute("name", "ThreadGroup.ramp_time");
      Text spText3 = this.dom.createTextNode("1");
      spEle3.appendChild(spText3);
      tgEle.appendChild(spEle3);
      
      Element lpEle = this.dom.createElement("longProp");
      lpEle.setAttribute("name", "ThreadGroup.start_time");
      Text lpText = this.dom.createTextNode("1345544661000");
      lpEle.appendChild(lpText);
      tgEle.appendChild(lpEle);
      
      Element lpEle2 = this.dom.createElement("longProp");
      lpEle2.setAttribute("name", "ThreadGroup.end_time");
      Text lpText2 = this.dom.createTextNode("1345544661000");
      lpEle2.appendChild(lpText2);
      tgEle.appendChild(lpEle2);
      
      Element bpEle2 = this.dom.createElement("boolProp");
      bpEle2.setAttribute("name", "ThreadGroup.scheduler");
      Text bpText2 = this.dom.createTextNode("false");
      bpEle2.appendChild(bpText2);
      tgEle.appendChild(bpEle2);
      
      Element spEle4 = this.dom.createElement("stringProp");
      spEle4.setAttribute("name", "ThreadGroup.duration");
      Text spText4 = this.dom.createTextNode("");
      spEle4.appendChild(spText4);
      tgEle.appendChild(spEle4);
      
      Element spEle5 = this.dom.createElement("stringProp");
      spEle5.setAttribute("name", "ThreadGroup.delay");
      Text spText5 = this.dom.createTextNode("");
      spEle5.appendChild(spText5);
      tgEle.appendChild(spEle5);
      
      hashTreeEle2 = this.dom.createElement("hashTree");
      hashTreeEle.appendChild(hashTreeEle2);
      
      Element vrtEle = this.dom.createElement("ResultCollector");
      vrtEle.setAttribute("guiclass", "ViewResultsFullVisualizer");
      vrtEle.setAttribute("testclass", "ResultCollector");
      vrtEle.setAttribute("testname", "View Results Tree");
      vrtEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(vrtEle);
      
      Element vbpEle = this.dom.createElement("boolProp");
      vbpEle.setAttribute("name", "ResultCollector.error_logging");
      Text vbpEleText = this.dom.createTextNode("false");
      vbpEle.appendChild(vbpEleText);
      vrtEle.appendChild(vbpEle);
      
      Element vopEle = this.dom.createElement("objProp");
      vrtEle.appendChild(vopEle);
      
      Element nameEle = this.dom.createElement("name");
      Text nameEleText = this.dom.createTextNode("saveConfig");
      nameEle.appendChild(nameEleText);
      vopEle.appendChild(nameEle);
      
      Element valEle = this.dom.createElement("value");
      valEle.setAttribute("class", "SampleSaveConfiguration");
      vopEle.appendChild(valEle);
      
      Element timeEle = this.dom.createElement("time");
      Text timeEleText = this.dom.createTextNode("true");
      timeEle.appendChild(timeEleText);
      valEle.appendChild(timeEle);
      
      Element latencyEle = this.dom.createElement("latency");
      Text latencyEleText = this.dom.createTextNode("true");
      latencyEle.appendChild(latencyEleText);
      valEle.appendChild(latencyEle);
      
      Element tsEle = this.dom.createElement("timestamp");
      Text tsEleText = this.dom.createTextNode("true");
      tsEle.appendChild(tsEleText);
      valEle.appendChild(tsEle);
      
      Element successEle = this.dom.createElement("success");
      Text successEleText = this.dom.createTextNode("true");
      successEle.appendChild(successEleText);
      valEle.appendChild(successEle);
      
      Element labelEle = this.dom.createElement("label");
      Text labelEleText = this.dom.createTextNode("true");
      labelEle.appendChild(labelEleText);
      valEle.appendChild(labelEle);
      
      Element codeEle = this.dom.createElement("code");
      Text codeEleText = this.dom.createTextNode("true");
      codeEle.appendChild(codeEleText);
      valEle.appendChild(codeEle);
      
      Element messageEle = this.dom.createElement("message");
      Text messageEleText = this.dom.createTextNode("true");
      messageEle.appendChild(messageEleText);
      valEle.appendChild(messageEle);
      
      Element tnEle = this.dom.createElement("threadName");
      Text tnEleText = this.dom.createTextNode("true");
      tnEle.appendChild(tnEleText);
      valEle.appendChild(tnEle);
      
      Element dtEle = this.dom.createElement("dataType");
      Text dtEleText = this.dom.createTextNode("true");
      dtEle.appendChild(dtEleText);
      valEle.appendChild(dtEle);
      
      Element encodingEle = this.dom.createElement("encoding");
      Text encodingEleText = this.dom.createTextNode("false");
      encodingEle.appendChild(encodingEleText);
      valEle.appendChild(encodingEle);
      
      Element asseEle = this.dom.createElement("assertions");
      Text asseEleText = this.dom.createTextNode("true");
      asseEle.appendChild(asseEleText);
      valEle.appendChild(asseEle);
      
      Element srEle = this.dom.createElement("subresults");
      Text srEleText = this.dom.createTextNode("true");
      srEle.appendChild(srEleText);
      valEle.appendChild(srEle);
      
      Element rdEle = this.dom.createElement("responseData");
      Text rdEleText = this.dom.createTextNode("false");
      rdEle.appendChild(rdEleText);
      valEle.appendChild(rdEle);
      
      Element sdEle = this.dom.createElement("samplerData");
      Text sdEleText = this.dom.createTextNode("false");
      sdEle.appendChild(sdEleText);
      valEle.appendChild(sdEle);
      
      Element xmlEle = this.dom.createElement("xml");
      Text xmlEleText = this.dom.createTextNode("true");
      xmlEle.appendChild(xmlEleText);
      valEle.appendChild(xmlEle);
      
      Element fnEle = this.dom.createElement("fieldNames");
      Text fnEleText = this.dom.createTextNode("false");
      fnEle.appendChild(fnEleText);
      valEle.appendChild(fnEle);
      
      Element rhEle = this.dom.createElement("responseHeaders");
      Text rhEleText = this.dom.createTextNode("false");
      rhEle.appendChild(rhEleText);
      valEle.appendChild(rhEle);
      
      Element reqEle = this.dom.createElement("requestHeaders");
      Text reqEleText = this.dom.createTextNode("false");
      reqEle.appendChild(reqEleText);
      valEle.appendChild(reqEle);
      
      Element rdeEle = this.dom.createElement("responseDataOnError");
      Text rdeEleText = this.dom.createTextNode("false");
      rdeEle.appendChild(rdeEleText);
      valEle.appendChild(rdeEle);
      
      Element sarfmEle = this.dom.createElement("saveAssertionResultsFailureMessage");
      Text sarfmEleText = this.dom.createTextNode("false");
      sarfmEle.appendChild(sarfmEleText);
      valEle.appendChild(sarfmEle);
      
      Element artsEle = this.dom.createElement("assertionsResultsToSave");
      Text artsEleText = this.dom.createTextNode("0");
      artsEle.appendChild(artsEleText);
      valEle.appendChild(artsEle);
      
      Element bytesEle = this.dom.createElement("bytes");
      Text bytesEle2 = this.dom.createTextNode("true");
      bytesEle.appendChild(bytesEle2);
      valEle.appendChild(bytesEle);
      
      Element vspEle = this.dom.createElement("stringProp");
      vspEle.setAttribute("name", "filename");
      vrtEle.appendChild(vspEle);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
    return hashTreeEle2;
  }
  
  public void addTC(Element hashTreeEle, String trnxName, List namelist, List valuelist, String protocol, String host, String port, String path, List regName, List regeX, List regOrd, List asNam, List lrtt)
  {
    try
    {
      Element cmEle = this.dom.createElement("TransactionController");
      cmEle.setAttribute("guiclass", "TransactionControllerGui");
      cmEle.setAttribute("testclass", "TransactionController");
      cmEle.setAttribute("testname", "T_" + trnxName);
      cmEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(cmEle);
      
      Element bopEle = this.dom.createElement("boolProp");
      bopEle.setAttribute("name", "TransactionController.parent");
      Text bopText = this.dom.createTextNode("false");
      bopEle.appendChild(bopText);
      cmEle.appendChild(bopEle);
      
      Element bopEle2 = this.dom.createElement("boolProp");
      bopEle2.setAttribute("name", "TransactionController.includeTimers");
      Text bopText2 = this.dom.createTextNode("false");
      bopEle2.appendChild(bopText2);
      cmEle.appendChild(bopEle2);
      
      Element hashTreeEle2 = this.dom.createElement("hashTree");
      hashTreeEle.appendChild(hashTreeEle2);
      
      Element httpSam = this.dom.createElement("HTTPSampler");
      httpSam.setAttribute("guiclass", "HttpTestSampleGui");
      httpSam.setAttribute("testclass", "HTTPSampler");
      httpSam.setAttribute("testname", trnxName);
      httpSam.setAttribute("enabled", "true");
      hashTreeEle2.appendChild(httpSam);
      
      Element hEpro = this.dom.createElement("elementProp");
      hEpro.setAttribute("name", "HTTPsampler.Arguments");
      hEpro.setAttribute("elementType", "Arguments");
      hEpro.setAttribute("guiclass", "HTTPArgumentsPanel");
      hEpro.setAttribute("testclass", "Arguments");
      hEpro.setAttribute("testname", "Argument List");
      hEpro.setAttribute("enabled", "true");
      httpSam.appendChild(hEpro);
      
      Element hCpro = this.dom.createElement("collectionProp");
      hCpro.setAttribute("name", "Arguments.arguments");
      hEpro.appendChild(hCpro);
      if (namelist.size() >= valuelist.size()) {
        for (int i = 0; i < valuelist.size(); i++)
        {
          if (namelist.get(i).toString().startsWith("{")) {
            namelist.set(i, "$" + namelist.get(i).toString());
          }
          if (valuelist.get(i).toString().startsWith("{")) {
            valuelist.set(i, "$" + valuelist.get(i).toString());
          }
          addParam(hCpro, namelist.get(i).toString(), valuelist.get(i).toString());
        }
      }
      if (valuelist.size() > namelist.size()) {
        for (int i = 0; i < namelist.size(); i++)
        {
          if (namelist.get(i).toString().startsWith("{")) {
            namelist.set(i, "$" + namelist.get(i).toString());
          }
          if (valuelist.get(i).toString().startsWith("{")) {
            valuelist.set(i, "$" + valuelist.get(i).toString());
          }
          addParam(hCpro, namelist.get(i).toString(), valuelist.get(i).toString());
        }
      }
      Element hSpro = this.dom.createElement("stringProp");
      hSpro.setAttribute("name", "HTTPSampler.domain");
      Text hSproText = this.dom.createTextNode(host);
      hSpro.appendChild(hSproText);
      httpSam.appendChild(hSpro);
      
      Element hSpro2 = this.dom.createElement("stringProp");
      hSpro2.setAttribute("name", "HTTPSampler.port");
      Text hSproText2 = this.dom.createTextNode(port);
      hSpro2.appendChild(hSproText2);
      httpSam.appendChild(hSpro2);
      
      Element hSpro3 = this.dom.createElement("stringProp");
      hSpro3.setAttribute("name", "HTTPSampler.connect_timeout");
      Text hSproText3 = this.dom.createTextNode("");
      hSpro3.appendChild(hSproText3);
      httpSam.appendChild(hSpro3);
      
      Element hSpro4 = this.dom.createElement("stringProp");
      hSpro4.setAttribute("name", "HTTPSampler.response_timeout");
      Text hSproText4 = this.dom.createTextNode("");
      hSpro4.appendChild(hSproText4);
      httpSam.appendChild(hSpro4);
      
      Element hSpro5 = this.dom.createElement("stringProp");
      hSpro5.setAttribute("name", "HTTPSampler.protocol");
      Text hSproText5 = this.dom.createTextNode(protocol);
      hSpro5.appendChild(hSproText5);
      httpSam.appendChild(hSpro5);
      
      Element hSpro6 = this.dom.createElement("stringProp");
      hSpro6.setAttribute("name", "HTTPSampler.contentEncoding");
      Text hSproText6 = this.dom.createTextNode("");
      hSpro6.appendChild(hSproText6);
      httpSam.appendChild(hSpro6);
      
      Element hSpro7 = this.dom.createElement("stringProp");
      hSpro7.setAttribute("name", "HTTPSampler.path");
      Text hSproText7 = this.dom.createTextNode(path);
      hSpro7.appendChild(hSproText7);
      httpSam.appendChild(hSpro7);
      
      Element hSpro8 = this.dom.createElement("stringProp");
      hSpro8.setAttribute("name", "HTTPSampler.method");
      Text hSproText8 = this.dom.createTextNode("POST");
      hSpro8.appendChild(hSproText8);
      httpSam.appendChild(hSpro8);
      
      Element hBpro = this.dom.createElement("boolProp");
      hBpro.setAttribute("name", "HTTPSampler.follow_redirects");
      Text hBproText = this.dom.createTextNode("true");
      hBpro.appendChild(hBproText);
      httpSam.appendChild(hBpro);
      
      Element hBpro2 = this.dom.createElement("boolProp");
      hBpro2.setAttribute("name", "HTTPSampler.auto_redirects");
      Text hBproText2 = this.dom.createTextNode("false");
      hBpro2.appendChild(hBproText2);
      httpSam.appendChild(hBpro2);
      
      Element hBpro3 = this.dom.createElement("boolProp");
      hBpro3.setAttribute("name", "HTTPSampler.use_keepalive");
      Text hBproText3 = this.dom.createTextNode("true");
      hBpro3.appendChild(hBproText3);
      httpSam.appendChild(hBpro3);
      
      Element hBpro4 = this.dom.createElement("boolProp");
      hBpro4.setAttribute("name", "HTTPSampler.DO_MULTIPART_POST");
      Text hBproText4 = this.dom.createTextNode("false");
      hBpro4.appendChild(hBproText4);
      httpSam.appendChild(hBpro4);
      
      Element hBpro5 = this.dom.createElement("boolProp");
      hBpro5.setAttribute("name", "HTTPSampler.monitor");
      Text hBproText5 = this.dom.createTextNode("false");
      hBpro5.appendChild(hBproText5);
      httpSam.appendChild(hBpro5);
      
      Element hSpro9 = this.dom.createElement("stringProp");
      hSpro9.setAttribute("name", "HTTPSampler.embedded_url_re");
      Text hSproText9 = this.dom.createTextNode("");
      hSpro9.appendChild(hSproText9);
      httpSam.appendChild(hSpro9);
      
      Element hashTreeEle3 = this.dom.createElement("hashTree");
      hashTreeEle2.appendChild(hashTreeEle3);
      for (int i = 0; i < regName.size(); i++) {
        addregX(hashTreeEle3, regName.get(i).toString(), regeX.get(i).toString(), regOrd.get(i).toString());
      }
      for (int i = 0; i < asNam.size(); i++) {
        addRA(hashTreeEle3, asNam.get(i).toString());
      }
      for (int i = 0; i < lrtt.size(); i++) {
        addCT(hashTreeEle3, lrtt.get(i).toString());
      }
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addWebTC(Element hashTreeEle, String trnxName, String wsurl, String wsbody, String wssa, List xfind, List lrtt, List xpathvalue)
  {
    try
    {
      Element cmEle = this.dom.createElement("TransactionController");
      cmEle.setAttribute("guiclass", "TransactionControllerGui");
      cmEle.setAttribute("testclass", "TransactionController");
      cmEle.setAttribute("testname", "T_" + trnxName);
      cmEle.setAttribute("enabled", "true");
      hashTreeEle.appendChild(cmEle);
      
      Element bopEle = this.dom.createElement("boolProp");
      bopEle.setAttribute("name", "TransactionController.parent");
      Text bopText = this.dom.createTextNode("false");
      bopEle.appendChild(bopText);
      cmEle.appendChild(bopEle);
      
      Element bopEle2 = this.dom.createElement("boolProp");
      bopEle2.setAttribute("name", "TransactionController.includeTimers");
      Text bopText2 = this.dom.createTextNode("false");
      bopEle2.appendChild(bopText2);
      cmEle.appendChild(bopEle2);
      
      Element hashTreeEle2 = this.dom.createElement("hashTree");
      hashTreeEle.appendChild(hashTreeEle2);
      
      Element rpcSam = this.dom.createElement("SoapSampler");
      rpcSam.setAttribute("guiclass", "SoapSamplerGui");
      rpcSam.setAttribute("testclass", "SoapSampler");
      rpcSam.setAttribute("testname", trnxName);
      rpcSam.setAttribute("enabled", "true");
      hashTreeEle2.appendChild(rpcSam);
      
      Element hEpro = this.dom.createElement("elementProp");
      hEpro.setAttribute("name", "HTTPsampler.Arguments");
      hEpro.setAttribute("elementType", "Arguments");
      rpcSam.appendChild(hEpro);
      
      Element collpro = this.dom.createElement("collectionProp");
      collpro.setAttribute("name", "Arguments.arguments");
      hEpro.appendChild(collpro);
      
      Element hSpro = this.dom.createElement("stringProp");
      hSpro.setAttribute("name", "SoapSampler.URL_DATA");
      Text hSproText = this.dom.createTextNode(wsurl);
      hSpro.appendChild(hSproText);
      rpcSam.appendChild(hSpro);
      
      Element hSpro2 = this.dom.createElement("stringProp");
      hSpro2.setAttribute("name", "HTTPSamper.xml_data");
      Text hSproText2 = this.dom.createTextNode(wsbody);
      hSpro2.appendChild(hSproText2);
      rpcSam.appendChild(hSpro2);
      
      Element hSpro3 = this.dom.createElement("stringProp");
      hSpro3.setAttribute("name", "SoapSampler.xml_data_file");
      Text hSproText3 = this.dom.createTextNode("");
      hSpro3.appendChild(hSproText3);
      rpcSam.appendChild(hSpro3);
      
      Element hSpro4 = this.dom.createElement("stringProp");
      hSpro4.setAttribute("name", "SoapSampler.SOAP_ACTION");
      Text hSproText4 = this.dom.createTextNode(wssa);
      hSpro4.appendChild(hSproText4);
      rpcSam.appendChild(hSpro4);
      
      Element hSpro5 = this.dom.createElement("stringProp");
      hSpro5.setAttribute("name", "SoapSampler.SEND_SOAP_ACTION");
      Text hSproText5 = this.dom.createTextNode("true");
      hSpro5.appendChild(hSproText5);
      rpcSam.appendChild(hSpro5);
      
      Element boolpro = this.dom.createElement("boolProp");
      boolpro.setAttribute("name", "HTTPSampler.use_keepalive");
      Text boolproText5 = this.dom.createTextNode("false");
      boolpro.appendChild(boolproText5);
      rpcSam.appendChild(boolpro);
      
      Element hashTreeEle3 = this.dom.createElement("hashTree");
      hashTreeEle2.appendChild(hashTreeEle3);
      for (int i = 0; i < xpathvalue.size(); i++) {
        addXPath(hashTreeEle3, xpathvalue.get(i).toString(), trnxName);
      }
      for (int i = 0; i < xfind.size(); i++) {
        addRA(hashTreeEle3, xfind.get(i).toString());
      }
      for (int i = 0; i < lrtt.size(); i++) {
        addCT(hashTreeEle3, lrtt.get(i).toString());
      }
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addParam(Element hCpro, String pname, String pvalue)
  {
    try
    {
      Element Epro = this.dom.createElement("elementProp");
      Epro.setAttribute("name", "continueClicked");
      Epro.setAttribute("elementType", "HTTPArgument");
      hCpro.appendChild(Epro);
      
      Element Spro = this.dom.createElement("stringProp");
      Spro.setAttribute("name", "HTTPArgument.always_encode");
      Text SproText = this.dom.createTextNode("true");
      Spro.appendChild(SproText);
      Epro.appendChild(Spro);
      
      Element Spro2 = this.dom.createElement("stringProp");
      Spro2.setAttribute("name", "Argument.value");
      Text SproText2 = this.dom.createTextNode(pvalue);
      Spro2.appendChild(SproText2);
      Epro.appendChild(Spro2);
      
      Element Spro3 = this.dom.createElement("stringProp");
      Spro3.setAttribute("name", "Argument.name");
      Text SproText3 = this.dom.createTextNode(pname);
      Spro3.appendChild(SproText3);
      Epro.appendChild(Spro3);
      
      Element Spro4 = this.dom.createElement("stringProp");
      Spro4.setAttribute("name", "Argument.metadata");
      Text SproText4 = this.dom.createTextNode("=");
      Spro4.appendChild(SproText4);
      Epro.appendChild(Spro4);
      
      Element Spro5 = this.dom.createElement("stringProp");
      Spro5.setAttribute("name", "Argument.use_equals");
      Text SproText5 = this.dom.createTextNode("true");
      Spro5.appendChild(SproText5);
      Epro.appendChild(Spro5);
      
      Element Bpro = this.dom.createElement("boolProp");
      Bpro.setAttribute("name", "HTTPArgument.use_equals");
      Text BproText = this.dom.createTextNode("true");
      Bpro.appendChild(BproText);
      Epro.appendChild(Bpro);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addregX(Element hashTreeEle3, String rname, String rex, String regOrd)
  {
    try
    {
      Element regX = this.dom.createElement("RegexExtractor");
      regX.setAttribute("guiclass", "RegexExtractorGui");
      regX.setAttribute("testclass", "RegexExtractor");
      regX.setAttribute("testname", rname);
      regX.setAttribute("enabled", "true");
      hashTreeEle3.appendChild(regX);
      
      Element rxSpro = this.dom.createElement("stringProp");
      rxSpro.setAttribute("name", "RegexExtractor.useHeaders");
      Text rxSproText = this.dom.createTextNode("false");
      rxSpro.appendChild(rxSproText);
      regX.appendChild(rxSpro);
      
      Element rxSpro2 = this.dom.createElement("stringProp");
      rxSpro2.setAttribute("name", "RegexExtractor.refname");
      Text rxSproText2 = this.dom.createTextNode(rname);
      rxSpro2.appendChild(rxSproText2);
      regX.appendChild(rxSpro2);
      
      Element rxSpro3 = this.dom.createElement("stringProp");
      rxSpro3.setAttribute("name", "RegexExtractor.regex");
      Text rxSproText3 = this.dom.createTextNode(rex);
      rxSpro3.appendChild(rxSproText3);
      regX.appendChild(rxSpro3);
      
      Element rxSpro4 = this.dom.createElement("stringProp");
      rxSpro4.setAttribute("name", "RegexExtractor.template");
      Text rxSproText4 = this.dom.createTextNode("$1$");
      rxSpro4.appendChild(rxSproText4);
      regX.appendChild(rxSpro4);
      
      Element rxSpro5 = this.dom.createElement("stringProp");
      rxSpro5.setAttribute("name", "RegexExtractor.default");
      Text rxSproText5 = this.dom.createTextNode(rname + " null");
      rxSpro5.appendChild(rxSproText5);
      regX.appendChild(rxSpro5);
      
      Element rxSpro6 = this.dom.createElement("stringProp");
      rxSpro6.setAttribute("name", "RegexExtractor.match_number");
      Text rxSproText6 = this.dom.createTextNode(regOrd);
      rxSpro6.appendChild(rxSproText6);
      regX.appendChild(rxSpro6);
      
      Element hashTreeEle4 = this.dom.createElement("hashTree");
      hashTreeEle3.appendChild(hashTreeEle4);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addXPath(Element hashTreeEle3, String xpath, String trnxName)
  {
    try
    {
      Element resA = this.dom.createElement("XPathExtractor");
      resA.setAttribute("guiclass", "XPathExtractorGui");
      resA.setAttribute("testclass", "XPathExtractor");
      resA.setAttribute("testname", trnxName + "_XPath Extractor");
      resA.setAttribute("enabled", "true");
      hashTreeEle3.appendChild(resA);
      
      Element raCSpro = this.dom.createElement("stringProp");
      raCSpro.setAttribute("name", "XPathExtractor.default");
      Text raCSproText = this.dom.createTextNode("XPath not found");
      raCSpro.appendChild(raCSproText);
      resA.appendChild(raCSpro);
      
      Element raSpro = this.dom.createElement("stringProp");
      raSpro.setAttribute("name", "XPathExtractor.refname");
      Text raSproText = this.dom.createTextNode(trnxName);
      raSpro.appendChild(raSproText);
      resA.appendChild(raSpro);
      
      Element raSpro2 = this.dom.createElement("stringProp");
      raSpro2.setAttribute("name", "XPathExtractor.xpathQuery");
      Text raSproText2 = this.dom.createTextNode(xpath);
      raSpro2.appendChild(raSproText2);
      resA.appendChild(raSpro2);
      
      Element raBpro = this.dom.createElement("boolProp");
      raBpro.setAttribute("name", "XPathExtractor.validate");
      Text raBproText = this.dom.createTextNode("false");
      raBpro.appendChild(raBproText);
      resA.appendChild(raBpro);
      
      Element raBpro2 = this.dom.createElement("boolProp");
      raBpro2.setAttribute("name", "XPathExtractor.tolerant");
      Text raBproText2 = this.dom.createTextNode("false");
      raBpro2.appendChild(raBproText2);
      resA.appendChild(raBpro2);
      
      Element raIpro = this.dom.createElement("boolProp");
      raIpro.setAttribute("name", "XPathExtractor.namespace");
      Text raIproText = this.dom.createTextNode("true");
      raIpro.appendChild(raIproText);
      resA.appendChild(raIpro);
      
      Element hashTreeEle5 = this.dom.createElement("hashTree");
      hashTreeEle3.appendChild(hashTreeEle5);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addRA(Element hashTreeEle3, String asNam)
  {
    try
    {
      Element resA = this.dom.createElement("ResponseAssertion");
      resA.setAttribute("guiclass", "AssertionGui");
      resA.setAttribute("testclass", "ResponseAssertion");
      resA.setAttribute("testname", asNam + "_Response_Validation");
      resA.setAttribute("enabled", "true");
      hashTreeEle3.appendChild(resA);
      
      Element raCpro = this.dom.createElement("collectionProp");
      raCpro.setAttribute("name", "Asserion.test_strings");
      resA.appendChild(raCpro);
      
      Element raCSpro = this.dom.createElement("stringProp");
      raCSpro.setAttribute("name", "-2088358912");
      Text raCSproText = this.dom.createTextNode(asNam);
      raCSpro.appendChild(raCSproText);
      raCpro.appendChild(raCSpro);
      
      Element raSpro = this.dom.createElement("stringProp");
      raSpro.setAttribute("name", "Assertion.test_field");
      Text raSproText = this.dom.createTextNode("Assertion.response_data");
      raSpro.appendChild(raSproText);
      resA.appendChild(raSpro);
      
      Element raBpro = this.dom.createElement("boolProp");
      raBpro.setAttribute("name", "Assertion.assume_success");
      Text raBproText = this.dom.createTextNode("false");
      raBpro.appendChild(raBproText);
      resA.appendChild(raBpro);
      
      Element raIpro = this.dom.createElement("intProp");
      raIpro.setAttribute("name", "Assertion.test_type");
      Text raIproText = this.dom.createTextNode("2");
      raIpro.appendChild(raIproText);
      resA.appendChild(raIpro);
      
      Element hashTreeEle5 = this.dom.createElement("hashTree");
      hashTreeEle3.appendChild(hashTreeEle5);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public void addCT(Element hashTreeEle3, String lrtt)
  {
    try
    {
      Element consT = this.dom.createElement("ConstantTimer");
      consT.setAttribute("guiclass", "ConstantTimerGui");
      consT.setAttribute("testclass", "ConstantTimer");
      consT.setAttribute("testname", "Think Time");
      consT.setAttribute("enabled", "true");
      hashTreeEle3.appendChild(consT);
      
      Element ctSpro = this.dom.createElement("stringProp");
      ctSpro.setAttribute("name", "ConstantTimer.delay");
      Text ctSproText = this.dom.createTextNode(lrtt + "000");
      ctSpro.appendChild(ctSproText);
      consT.appendChild(ctSpro);
      
      Element hashTreeEle6 = this.dom.createElement("hashTree");
      hashTreeEle3.appendChild(hashTreeEle6);
    }
    catch (Exception ie)
    {
      ie.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    XMLCreator xce = new XMLCreator();
    
    xce.runExample();
  }
}
