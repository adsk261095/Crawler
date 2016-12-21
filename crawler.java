
package UrlExceptions;
import java.io.*;
import java.lang.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyException {
    private static Formatter x;
    public void inside_product(String newlink,String text,Formatter x)
    { 
        try
        {String []a=new String[15];
          
        Document docs = Jsoup.connect(newlink).get();
	Elements contents = docs.getElementsByClass("stats");
	
	for (Element link : contents) 
        {
        Elements rows=link.select("tr");
	Element row1 = rows.get(rows.size()-1);
	Elements cols1 = row1.select("td");
        	for (int ip = 1; ip < cols1.size()-1; ip++)
                { 
	         Element Cols12 = cols1.get(ip);
      	         a[ip]=Cols12.html().replaceAll("\\s","");
		        }
	}
       
      x.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",newlink,text,a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12],a[13]);  
      }
        
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void in_page(String url,String purl,Formatter x)
    {
        try
        {
            Document d=Jsoup.connect(url+purl).get();
            Elements e=d.getElementsByClass("listtable");
            Elements el=e.select("a[href]");
            for(Element ele:el)
            {
                if(ele.attr("href").contains("//www.cvedetails.com/product/"))
                {
                    System.out.println("product details"+ele.attr("href"));    
                     inside_product("https:"+ele.attr("href"),ele.text(),x);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void letterlist(String url,String purl,Formatter x)
    {
        try
        {
        Document doc=Jsoup.connect(url+purl).get();
        Elements lists=doc.getElementsByClass("paging");
        Elements e=lists.select("a[href]");
        for(Element list:e)
        {
            System.out.println("pagging"+list.attr("href"));
            in_page(url,list.attr("href"),x);
        }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        String url="http://www.cvedetails.com",purl="";
        MyException m=new MyException();
       try
        {
            x=new Formatter("D:\\AMAN\\java\\anoop sir\\Web Crawler\\webcrawler.csv");
            x.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n","LINK","Name of Product","DOS","Code of Execuetion","Overflow","Memory Corruption","ql injection","XSS","Directory traversal","Http response splitting","Bypass something","Gain information","Gain privilages","CSRF","File inclusion");
            
            Document doc=Jsoup.connect(url).get();
            Elements links=doc.select("a[href]");
            for(Element link:links)
            {
                if(link.text().equals("Products"))
                {   
                    purl=url+link.attr("href");
                    break;
                }
            }
            System.out.println("product page"+purl);
            Document d=Jsoup.connect(purl).get();
            Elements e=d.getElementsByClass("letterlist");
            Elements e1=e.select("a[href]");
            for(Element l:e1)
            {
                if(l.attr("href").contains("/product-list/firstchar-"))
                {
                    System.out.println("letter list"+l.attr("href"));
                    m.letterlist(url,l.attr("href"),x);
                }
            }
            }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            x.close();
        }
    }
    
}
