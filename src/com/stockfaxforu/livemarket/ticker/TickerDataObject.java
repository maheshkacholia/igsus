package com.stockfaxforu.livemarket.ticker;
///////////////////////////////////////////////////////////
// DeJaved by mDeJava v1.0. Copyright 1999 MoleSoftware. //
//       To download last version of this software:      //
//            http://molesoftware.hypermatr.net          //
//               e-mail:molesoftware@mail.ru             //
///////////////////////////////////////////////////////////


class TickerDataObject
{

    int start = 0;
    int end = 0;
    int lStart = 0;
    int lEnd = 0;
    int bottom = 0;
    int top = 0;
    boolean in = false;
    String link = null;
    char pm = 0;
    char seg = 0;
    final int maxParts = 6;
    String txt[] = null;
    boolean b[] = null;

    TickerDataObject()
    {
        link = "";
        txt = new String[6];
        b = new boolean[6];
    }
}
