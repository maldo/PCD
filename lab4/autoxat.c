#include <stdio.h>
main()
{
//creacio de la pagina html indicant que es del tipus html
printf("Content-type: text/html\n\n");
//Codi html
printf("<HTML>\n");
printf("<HEAD>\n");
printf("</HEAD>\n");
printf("<body>\n");
printf("<applet code=\"autoxat.class\"\n");
printf(" codebase=\"http://127.0.0.1:8080/autoxat/applets\"\n");
printf(" width=800 height=500>\n");
printf("</applet>\n");
printf("</body>\n");
printf("</HTML>\n");
}
