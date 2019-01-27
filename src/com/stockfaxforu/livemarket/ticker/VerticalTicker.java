package com.stockfaxforu.livemarket.ticker;
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class VerticalTicker extends Applet
    implements Runnable, MouseListener, MouseMotionListener
{

    String tickerData[] = null;
    String tmpTickerData[] = null;
    String tickerId = null;
    String tId = null;
    String dataSrc = null;
    String upImg = null;
    String downImg = null;
    Thread scroller = null;
    int delay1 = 0;
    int delay2 = 0;
    int delay = 0;
    int fdelay = 0;
    int totalDelay = 0;
    boolean scroll = false;
    boolean doSetTickerId = false;
    boolean newData = false;
    boolean resetting = false;
    Graphics offg = null;
    Graphics offg2 = null;
    Image offimg = null;
    Image offimg2 = null;
    Image upArrow = null;
    Image downArrow = null;
    MediaTracker tracker = null;
    int w = 0;
    int h = 0;
    int lt = 0;
    int rt = 0;
    int tp = 0;
    int bt = 0;
    int y = 0;
    int inc = 0;
    int t = 0;
    int strHt = 0;
    int imgX = 0;
    int imgY = 0;
    int imgW = 0;
    int imgH = 0;
    final int gap1 = 10;
    final int gap2 = 30;
    Color backColor = null;
    Color fontColor = null;
    Font font = null;
    Font bfont = null;
    FontMetrics fm = null;
    FontMetrics bfm = null;
    int mX = 0;
    int mY = 0;
    boolean mouseIn = false;
    boolean paused = false;
    boolean fast = false;
    boolean goUp = false;
    Cursor def_cur = null;
    Cursor hand_cur = null;
    String userId = null;
    TickerDataObject tdo[] = null;
    Vector tdoVec = null;
    final int maxParts = 6;
    boolean fromNSE = false;

    public VerticalTicker()
    {
    }

    public void init()
    {
        userId = getParameter("userid");
        dataSrc = getParameter("datasrc");
        w = getSize().width;
        h = getSize().height;
        tp = 2;
        bt = h - 2;
        String s = getParameter("bgcolor");
        if(s != null)
            backColor = new Color(Integer.parseInt(s, 16));
        else
            backColor = Color.white;
        String s1 = getParameter("font_color");
        if(s1 != null)
            fontColor = new Color(Integer.parseInt(s1, 16));
        else
            fontColor = Color.black;
        setBackground(backColor);
        URL url = getDocumentBase();
        String s2 = "www.nseindia.com";
        String s3 = "www.nse-india.com";
        String s4 = "nseindia.com";
        String s5 = "nse-india.com";
        String s6 = "172.20.3.163";
        String s7 = "pavan";
        String s8 = "sundarv";
        String s9 = url.getHost();
        if(s9.equals(s2) || s9.equals(s3) || s9.equals(s4) || s9.equals(s5) || s9.equals(s6) || s9.equals(s7) || s9.equals(s8))
        {
            fromNSE = true;
            String s10 = getParameter("scroll_delay1");
            if(s10 != null)
                delay1 = Integer.parseInt(s10);
            else
                delay1 = 15;
            String s11 = getParameter("scroll_delay2");
            if(s11 != null)
                delay2 = Integer.parseInt(s11);
            else
                delay2 = 7;
            delay = delay1;
            String s12 = getParameter("fetch_delay");
            if(s12 != null)
                fdelay = Integer.parseInt(s12);
            else
                fdelay = 0x2bf20;
            totalDelay = 0;
            upImg = getParameter("upArrow");
            downImg = getParameter("downArrow");
            upArrow = getImage(getDocumentBase(), upImg);
            downArrow = getImage(getDocumentBase(), downImg);
            tracker = new MediaTracker(this);
            tracker.addImage(upArrow, 0);
            tracker.addImage(downArrow, 0);
            try
            {
                tracker.waitForAll();
            }
            catch(InterruptedException interruptedexception)
            {
                return;
            }
            t = 0;
            String s13 = getParameter("ticker_font");
            if(s13 == null)
                s13 = "SansSerif";
            int i = 10;
            String s14 = getParameter("font_size");
            if(s14 != null)
                i = Integer.parseInt(s14);
            else
                i = 10;
            font = new Font(s13, 0, i);
            bfont = new Font(s13, 1, i);
            fm = getFontMetrics(font);
            bfm = getFontMetrics(bfont);
            strHt = fm.getHeight();
            lt = 5;
            rt = w - 5;
            scroll = false;
            if(scroller == null)
            {
                scroller = new Thread(this);
                scroller.start();
                scroll = true;
            }
            offimg = createImage(w, h);
            offg = offimg.getGraphics();
            offg.clipRect(2, 2, w - 4, h - 4);
            hand_cur = new Cursor(12);
            def_cur = new Cursor(0);
            addMouseListener(this);
            addMouseMotionListener(this);
            paused = false;
            fast = false;
            imgH = upArrow.getHeight(this);
            imgW = upArrow.getWidth(this);
            goUp = true;
            y = bt + strHt;
            inc = -1;
            tickerId = getParameter("tickerid");
            tickerData = getData(tickerId);
            processTDO();
        }
        else
        {
            fromNSE = false;
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        if(fromNSE)
        {
            setCursor(hand_cur);
            g.drawImage(offimg, 0, 0, this);
        }
        else
        {
            g.setFont(bfont);
            g.drawString("National Stock Exchange of India Limited", 10, 15);
        }
    }

    private void setTickerId(String s)
    {
        tickerId = s;
        tickerData = getData(tickerId);
        processTDO();
        if(goUp)
            y = bt + strHt;
        else
            y = tp;
        doSetTickerId = false;
    }

    public synchronized void callSetTickerId(String s)
    {
        tId = s;
        doSetTickerId = true;
    }

    public void run()
    {
        scroller.setPriority(1);
        while(scroll) 
        {
            if(doSetTickerId)
                setTickerId(tId);
            if(tickerData != null)
            {
                t = 0;
                drawFrame();
                try
                {
                    VerticalTicker _tmp = this;
                    Thread.sleep(delay);
                    totalDelay += delay;
                }
                catch(InterruptedException interruptedexception) { }
                if(!paused)
                    move();
            }
            if(totalDelay > fdelay)
            {
                totalDelay = 0;
                tmpTickerData = getData(tickerId);
                if(tmpTickerData != null)
                    newData = true;
            }
            if(resetting && newData)
            {
                newData = false;
                tickerData = tmpTickerData;
                processTDO();
            }
            resetting = false;
        }

    }

    private void drawFrame()
    {
        offg.setColor(backColor);
        offg.fillRect(0, 0, w, h);
        boolean flag = true;
        int i = 0;
        int j = 0;
        do
            if(goUp)
            {
                offg.setColor(fontColor);
                tdo[t].start = lt;
                tdo[t].lStart = lt + 2;
                int k = 0;
                for(int i1 = 0; i1 < 6; i1++)
                    if(tdo[t].b[i1])
                        k += fm.stringWidth(tdo[t].txt[i1]);
                    else
                        k += bfm.stringWidth(tdo[t].txt[i1]);

                tdo[t].end = tdo[t].start + k;
                String s = tdo[t].link;
                if(s.length() > 2 && tdo[t].seg == 'c')
                    s = s.substring(0, s.length() - 2);
                tdo[t].lEnd = tdo[t].lStart + fm.stringWidth(s);
                int k1 = y + j;
                tdo[t].bottom = k1 + 2;
                tdo[t].top = k1 - strHt;
                if(k1 > tp && k1 < bt + strHt)
                    tdo[t].in = true;
                else
                    tdo[t].in = false;
                if(tdo[t].link.length() > 0 && tdo[t].in && mouseIn && mX > tdo[t].start && mX < tdo[t].end && mY > k1 - strHt && mY < k1)
                    offg.drawLine(tdo[t].lStart, k1 + 3, tdo[t].lEnd, k1 + 3);
                if(tdo[t].in)
                {
                    int i2 = tdo[t].start;
                    for(int k2 = 0; k2 < 6; k2++)
                    {
                        String s2 = tdo[t].txt[k2];
                        if(tdo[t].b[k2])
                        {
                            offg.setFont(bfont);
                            offg.drawString(s2, i2, k1);
                            i2 += bfm.stringWidth(s2);
                        }
                        else
                        {
                            offg.setFont(font);
                            offg.drawString(s2, i2, k1);
                            i2 += fm.stringWidth(s2);
                        }
                    }

                }
                imgY = k1 - imgH;
                imgX = lt + k;
                if(tdo[t].pm == '+')
                {
                    offg.drawImage(upArrow, imgX, imgY, this);
                    i += imgW;
                }
                else
                if(tdo[t].pm == '-')
                {
                    offg.drawImage(downArrow, imgX, imgY, this);
                    i += imgW;
                }
                j += 2 * strHt;
                t++;
                if(t == tickerData.length)
                {
                    flag = false;
                    if(k1 < tp)
                        reset();
                }
                if(k1 > bt + strHt)
                    flag = false;
            }
            else
            {
                offg.setColor(fontColor);
                tdo[t].start = lt;
                tdo[t].lStart = lt + 2;
                int l = 0;
                for(int j1 = 0; j1 < 6; j1++)
                    if(tdo[t].b[j1])
                        l += fm.stringWidth(tdo[t].txt[j1]);
                    else
                        l += bfm.stringWidth(tdo[t].txt[j1]);

                tdo[t].end = tdo[t].start + l;
                String s1 = tdo[t].link;
                if(s1.length() > 2 && tdo[t].seg == 'c')
                    s1 = s1.substring(0, s1.length() - 2);
                tdo[t].lEnd = tdo[t].lStart + fm.stringWidth(s1);
                int l1 = y - j;
                tdo[t].bottom = l1 + 2;
                tdo[t].top = l1 - strHt;
                if(l1 > tp && l1 < bt + strHt)
                    tdo[t].in = true;
                else
                    tdo[t].in = false;
                if(tdo[t].link.length() > 0 && tdo[t].in && mouseIn && mX > tdo[t].start && mX < tdo[t].end && mY > l1 - strHt && mY < l1)
                    offg.drawLine(tdo[t].lStart, l1 + 3, tdo[t].lEnd, l1 + 3);
                if(tdo[t].in)
                {
                    int j2 = tdo[t].start;
                    for(int l2 = 0; l2 < 6; l2++)
                    {
                        String s3 = tdo[t].txt[l2];
                        if(tdo[t].b[l2])
                        {
                            offg.setFont(bfont);
                            offg.drawString(s3, j2, l1);
                            j2 += bfm.stringWidth(s3);
                        }
                        else
                        {
                            offg.setFont(font);
                            offg.drawString(s3, j2, l1);
                            j2 += fm.stringWidth(s3);
                        }
                    }

                }
                imgY = l1 - imgH;
                imgX = lt + l;
                if(tdo[t].pm == '+')
                {
                    offg.drawImage(upArrow, imgX, imgY, this);
                    i += imgW;
                }
                else
                if(tdo[t].pm == '-')
                {
                    offg.drawImage(downArrow, imgX, imgY, this);
                    i += imgW;
                }
                j += 2 * strHt;
                t++;
                if(t == tickerData.length)
                {
                    flag = false;
                    if(l1 > bt + strHt)
                        reset();
                }
                if(l1 < tp - strHt)
                    flag = false;
            }
        while(flag);
        offg.setColor(backColor);
        offg.fillRect(rt, 0, w - rt, h);
        repaint();
    }

    private void move()
    {
        y += inc;
    }

    private void processTDO()
    {
        tdoVec = new Vector();
        for(int i = 0; i < tickerData.length; i++)
        {
            TickerDataObject tickerdataobject = new TickerDataObject();
            String s = tickerData[i];
            char c = s.charAt(0);
            if(c == '+' || c == '-')
                s = s.substring(1);
            tickerdataobject.pm = c;
            tickerdataobject.link = "";
            int j = s.indexOf("<l c>");
            if(j >= 0)
            {
                tickerdataobject.seg = 'c';
            }
            else
            {
                j = s.indexOf("<l f>");
                tickerdataobject.seg = 'f';
            }
            int k = s.indexOf("</l>");
            if(j >= 0 && k >= 0 && k > j)
            {
                String s1 = s.substring(j + 5, k);
                tickerdataobject.link = s1.trim();
                if(tickerdataobject.seg == 'c')
                    s1 = s1.substring(0, s1.length() - 2);
                s = s.substring(0, j) + s1 + s.substring(k + 4);
            }
            for(int l = 0; l < 6; l++)
            {
                tickerdataobject.txt[l] = "";
                tickerdataobject.b[l] = false;
            }

            int i1 = 0;
            int j1 = s.indexOf("<b>");
            int k1 = s.indexOf("</b>");
            do
            {
                if(j1 >= 0 && k1 >= 0 && k1 > j1)
                {
                    String s2 = s.substring(0, j1);
                    String s3 = s.substring(j1 + 3, k1);
                    tickerdataobject.txt[i1] = s2;
                    tickerdataobject.txt[i1 + 1] = s3;
                    tickerdataobject.b[i1] = false;
                    tickerdataobject.b[i1 + 1] = true;
                    i1 += 2;
                    s = s.substring(k1 + 4);
                }
                j1 = s.indexOf("<b>");
                k1 = s.indexOf("</b>");
            }
            while(j1 >= 0 && i1 < 6);
            if(i1 < 6 && s.length() != 0)
                tickerdataobject.txt[i1] = s;
            tdoVec.addElement(tickerdataobject);
        }

        tdo = new TickerDataObject[tdoVec.size()];
        tdoVec.copyInto(tdo);
    }

    private String[] getData(String s)
    {
        try
        {
            URL url = new URL(getCodeBase(), dataSrc);
            HttpMessage httpmessage = new HttpMessage(url);
            Properties properties = new Properties();
            properties.put("tickerid", s);
            properties.put("userid", userId);
            InputStream inputstream = httpmessage.sendGetMessage(properties);
            ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
            Object obj = objectinputstream.readObject();
            String as[] = (String[])obj;
            inputstream.close();
            objectinputstream.close();
            return as;
        }
        catch(Exception exception)
        {
        }
        return null;
    }

    public void destroy()
    {
        scroller = null;
        scroll = false;
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        mY = mouseevent.getY();
        if(mX > lt && mX < rt && mY > tp && mY < bt)
            mouseIn = true;
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        mouseIn = false;
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        mY = mouseevent.getY();
        if(mX > lt && mX < rt && mY > tp && mY < bt)
            mouseIn = true;
        else
            mouseIn = false;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        mY = mouseevent.getY();
        if((mouseevent.getModifiers() & 0x10) == 16)
        {
            for(int i = 0; i < tickerData.length; i++)
            {
                if(tdo[i].link.length() <= 0 || mX < tdo[i].start || mX > tdo[i].end || mY < tdo[i].top || mY > tdo[i].bottom)
                    continue;
                URL url = null;
                try
                {
                    if(tdo[i].seg == 'c')
                    {
                        String s = tdo[i].link;
                        s = replaceAmpersand(s);
                        String s2 = s.substring(0, s.length() - 2);
                        s2 = replaceAmpersand(s2);
                        String s3 = "http://www.nseindia.com/marketinfo/equities/cmquote.jsp?key=" + s + "N&symbol=" + s2 + "&flag=0";
                        url = new URL(s3);
                    }
                    else
                    {
                        String s1 = tdo[i].link;
                        StringTokenizer stringtokenizer = new StringTokenizer(s1, " ");
                        int j = stringtokenizer.countTokens();
                        String s4 = "";
                        String s5 = "";
                        for(int k = 0; stringtokenizer.hasMoreElements(); k++)
                        {
                            String s6 = stringtokenizer.nextToken();
                            s6 = replaceAmpersand(s6);
                            if(k == 1)
                                s4 = s6;
                            if(k == 2)
                                s6 = s6.substring(0, 2) + s6.substring(3, 6).toUpperCase() + s6.substring(7);
                            if(k == 4)
                                s6 = roundOff(s6);
                            s5 = s5 + s6;
                        }

                        if(j == 3)
                            s5 = s5 + "--";
                        url = new URL("http://www.nseindia.com/marketinfo/fo/foquote.jsp?key=" + s5 + "&symbol=" + s4 + "&flag=0");
                    }
                }
                catch(MalformedURLException malformedurlexception)
                {
                }
                getAppletContext().showDocument(url, "f2");
                break;
            }

        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    private String replaceAmpersand(String s)
    {
        String s1 = s;
        int i = s.indexOf("&");
        if(i != -1)
        {
            s1 = s.substring(0, i);
            s1 = s1 + "%26";
            s1 = s1 + s.substring(i + 1);
        }
        return s1;
    }

    public void pnp()
    {
        paused = !paused;
    }

    public void toggle_speed()
    {
        if(delay == delay2)
            delay = delay1;
        else
            delay = delay2;
    }

    public void reverse()
    {
        inc = -inc;
        goUp = !goUp;
        if(goUp)
            y = bt + strHt;
        else
            y = tp;
    }

    private void reset()
    {
        resetting = true;
        if(goUp)
            y = bt + strHt;
        else
            y = tp;
    }

    private String roundOff(String s)
    {
        int i = s.indexOf(".");
        int j = s.length();
        if(i != -1)
        {
            if(i == j - 2)
                s = s + "0";
        }
        else
        {
            s = s + ".00";
        }
        return s;
    }
}
