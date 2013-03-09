    /**
     * Vrati jmena vytezu, tak jak s nachazi v DBLP, CiteSeer, ci WoS.
     * Ceny: CoddA = SIGMOND E.F.Codd Innov. Award,
     *       ACMFellows,
     *       ACMTuring Award,
     *       ISI Highly Cited
     * @param typXML DBLP/CiteSeer/WoS
     * @param typCeny CoddA/ACMFellows/ACMTuring/ISI/(VSE)
     * @return
     */
    public static String[] getJmena(String typXML, String typCeny){
        HashMap<String, String[]> mapaJmen = new HashMap<String, String[]>();
      // Codd Innovation Award
        // DBLP
        String[] jmenaDBLP_Codd = {"E. F. CODD", "MICHAEL STONEBRAKER", "JIM GRAY", "PHILIP A. BERNSTEIN", "DAVID J. DEWITT", "C. MOHAN",
                            "DAVID MAIER", "SERGE ABITEBOUL", "HECTOR GARCIA-MOLINA", "RAKESH AGRAWAL", "RUDOLF BAYER",
                            "PATRICIA G. SELINGER", "DONALD D. CHAMBERLIN", "RONALD FAGIN", "MICHAEL J. CAREY", "JEFFREY D. ULLMAN",
                            "JENNIFER WIDOM", "MOSHE Y. VARDI", "MASARU KITSUREGAWA", "UMESHWAR DAYAL"};
        // CiteSeer
        String[] jmenaCiteSeer_Codd = {"E. F. CODD", "MICHAEL STONEBRAKER", "JIM GRAY", "PHILIP A. BERNSTEIN", "DAVID J. DEWITT", "C. MOHAN",
                            "DAVID MAIER", "SERGE ABITEBOUL", "HECTOR GARCIA-MOLINA", "RAKESH AGRAWAL", "RUDOLF BAYER",
                            "PATRICIA G. SELINGER", "DON CHAMBERLIN", "RONALD FAGIN", "MICHAEL J. CAREY", "JEFFREY D. ULLMAN",
                            "JENNIFER WIDOM", "MOSHE Y. VARDI", "MASARU KITSUREGAWA", "UMESHWAR DAYAL"};
        // WOS
        String[] jmenaWOS_Codd = {"ABITEBOUL, S", "AGRAWAL, R", "BERNSTEIN, P", "CAREY, J", "DAYAL, U", "DEWITT, D", "FAGIN, R", "GARCIA-MOLINA, H", "CHAMBERLIN, D", "KITSUREGAWA, M", "MAIER, D", "MOHAN, C", "STONEBRAKER, M", "VARDI, MY", "WIDOM, J"};
        // zarazeni do mapy jmen
        mapaJmen.put("CODDADBLP", jmenaDBLP_Codd);
        mapaJmen.put("CODDACITESEER", jmenaCiteSeer_Codd);
        mapaJmen.put("CODDAWOS", jmenaWOS_Codd);

      // ACM Fellows
        // DBLP
        String[] jmenaDBLP_ACMFell = {"SERGE ABITEBOUL", "W. RICHARDS ADRION", "SARITA V. ADVE", "ALFRED V. AHO", "IAN F. AKYILDIZ", "RAJEEV ALUR", "ANDREW W. APPEL", "HAGIT ATTIYA", "OUMLZALP BABAOGLU", "LUIZ ANDREACUTE BARROSO", "VICTOR R. BASILI", "KENNETH E. BATCHER", "CATRIEL BEERI", "HAL BERGHEL", "ELISA BERTINO", "LAXMI N. BHUYAN", "KENNETH P. BIRMAN", "JOSEACUTE A. BLAKELEY", "GUY E. BLELLOCH", "SHAHID H. BOKHARI", "GRADY BOOCH", "RONALD J. BRACHMAN", "YURI BREITBART", "JANIS A. BUBENKO JR.", "PETER BUNEMAN", "STUART K. CARD", "LUCA CARDELLI", "DONALD D. CHAMBERLIN", "BERNARD CHAZELLE", "IMRICH CHLAMTAC", "KENNETH L. CLARKSON", "J. DANIEL COUGER", "MARK CROVELLA", "MICHAEL DAHLIN", "THOMAS A. DEFANTI", "RICHARD A. DEMILLO", "DAVID J. DEWITT", "DAVID P. DOBKIN", "DANNY DOLEV", "JACK DONGARRA", "RONALD FAGIN", "RICHARD J. FATEMAN", "JOAN FEIGENBAUM", "MATTHIAS FELLEISEN", "JEANNE FERRANTE", "KENNETH D. FORBUS", "GEORGE W. FURNAS", "HAROLD N. GABOW", "M. R. GAREY", "JONA
 THAN GIB
 BONS", "DAVID K. GIFFORD", "LEO R. GOTLIEB", "LEONIDAS J. GUIBAS", "JOHN V. GUTTAG", "DONALD J. HADERLE", "BRENT HAILPERN", "JOSEPH Y. HALPERN", "JURIS HARTMANIS", "DAVID HECKERMAN", "PHILIP HEIDELBERGER", "MONIKA RAUCH HENZINGER", "MAURICE HERLIHY", "W. DANIEL HILLIS", "JOHN E. HOPCROFT", "RICHARD HULL", "TOSHIHIDE IBARAKI", "OSCAR H. IBARRA", "NEIL IMMERMAN", "H. V. JAGADISH", "FARNAM JAHANIAN", "M. FRANS KAASHOEK", "TAKEO KANADE", "HOWARD J. KARLOFF", "ZVI M. KEDEM", "CHARLES KELLOGG", "RICHARD B. KIEBURTZ", "JAMES E. KIRKPATRICK", "MARIA M. KLAWE", "JON C. KLENSIN", "PHOKION G. KOLAITIS", "JOSEPH A. KONSTAN", "HENRY F. KORTH", "S. RAO KOSARAJU", "DEXTER KOZEN", "DAVID J. KUCK", "JAMES F. KUROSE", "RICHARD E. LADNER", "S. LAKSHMIVARAHAN", "AXEL VAN LAMSWEERDE", "JAMES R. LARUS", "JOHN LAUNCHBURY", "STEPHEN S. LAVENBERG", "JOSHUA LEDERBERG", "CHARLES E. LEISERSON", "MAURIZIO LENZERINI", "NANCY G. LEVESON", "MARC LEVOY", "BARBARA LISKOV", "WITOLD LITWIN", "DAVID B. LOMET", 
 "DONALD 
 W. LOVELAND", "DAVID B. MACQUEEN", "DAHLIA MALKHI", "ZOHAR MANNA", "MARGARET MARTONOSI", "LARRY MASINTER", "PAUL R. MCJONES", "KURT MEHLHORN", "JEFFREY C. MOGUL", "RAJEEV MOTWANI", "ALICE H. MUNTZ", "RICHARD E. NANCE", "TAKAO NISHIZEKI", "PETER NORVIG", "DAVID NOTKIN", "MARTIN ODERSKY", "LEON J. OSTERWEIL", "JOHN K. OUSTERHOUT", "SUSAN S. OWICKI", "JAACUTENOS PACH", "DAVID A. PADUA", "KRISHNA V. PALEM", "CHERRI M. PANCAKE", "RANDY F. PAUSCH", "CAROLINE PAXSON", "NICHOLAS PIPPENGER", "HAMID PIRAHESH", "FRANCO P. PREPARATA", "KRITHI RAMAMRITHAM", "DAVID A. RANDELL", "JOHN H. REIF", "THOMAS W. REPS", "ARISTIDES A. G. REQUICHA", "MARTIN C. RINARD", "KRISHAN K. SABNANI", "DAVID SALESIN", "GERARD SALTON", "JEAN E. SAMMET", "RAVI S. SANDHU", "HANS-JOUMLRG SCHEK", "RICHARD D. SCHLICHTING", "ROBERT SEDGEWICK", "PATRICIA G. SELINGER", "BART SELMAN", "MARGO I. SELTZER", "KENNETH C. SEVCIK", "MICHA SHARIR", "DAVID B. SHMOYS", "BEN SHNEIDERMAN", "DANIEL P. SIEWIOREK", "MUKESH SINGHAL", "
 DONALD R
 . SLUTZ", "RICHARD T. SNODGRASS", "MARY LOU SOFFA", "EUGENE H. SPAFFORD", "ALFRED Z. SPECTOR", "KENNETH STEIGLITZ", "LARRY J. STOCKMEYER", "MICHAEL STONEBRAKER", "BJARNE STROUSTRUP", "GERALD J. SUSSMAN", "EACUTEVA TARDOS", "ROBERT ENDRE TARJAN", "MIKKEL THORUP", "MARTIN TOMPA", "JOSEP TORRELLAS", "IRVING L. TRAIGER", "ELI UPFAL", "MOSHE Y. VARDI", "VARGHESE S. JACOB", "VICTOR VIANU", "UZI VISHKIN", "JEFFREY SCOTT VITTER", "PHILIP WADLER", "DAVID L. WALTZ", "ROY WANT", "BEN WEGBREIT", "GERHARD WEIKUM", "ELAINE J. WEYUKER", "JENNIFER WIDOM", "ROBERT WILENSKY", "SHMUEL WINOGRAD", "MARIANNE WINSLETT", "DAVID S. WISE", "IAN H. WITTEN", "WILLIAM A. WULF", "MIHALIS YANNAKAKIS", "F. KENNETH ZADECK", "STANLEY B. ZDONIK", "ANDREAS ZELLER", "WILLY ZWAENEPOEL", "STUART H. ZWEBEN"};
        // CiteSeer
        String[] jmenaCiteSeer_ACMFell = {"MIGUEL ABADI", "S. ABITEBOUL", "GREGORY ABOWD", "W. RICHARDS ADRION", "SARITA ADVE ELECTRICAL", "ALFRED V. AHO", "NARENDRA AHUJA", "ERIC ALLENDER", "RAJEEV ALUR", "LORENZO ALVISI PH. D", "ANDREW W. APPEL", "MIKHAIL ATALLAH", "BRENTON ATCHISON", "HAGIT ATTIYA T", "LOUIS AUSLANDER", "RICHARD AUSTING", "ZALP BABAOGLU", "FRANCIS BACON", "AKHILESH BAJAJ", "RADIM S ARA RUZENA BAJCSY", "V. BASILI", "KENNETH BATCHER", "I. J. BATE", "CATRIEL BEERI", "ELISA BERTINO", "LAXMI N. BHUYAN", "OLIVER BIERMANN", "ANDERS BJORNER", "JOS'E A. BLAKELEY", "G. E. BLELLOCH", "ROBERT J. BOBROW", "GREGOR VON BOCHMANN", "PAUL T. BOGGS", "S. BORMAN", "GAETANO BORRIELLO", "PHILIP BOURNE", "RONALD J. BRACHMAN", "B. A. BRANDIN", "JANIS A. BUBENKO", "PETER BUNEMAN", "LUCA CARDELLI", "EDWIN CATMULL", "SIRISH CHANDRASEKARAN", "JENNIFER T. CHAYES", "BERNARD CHAZELLE", "COMER DUNCAN", "ANNE CONDON", "WILLIAM CROFT", "LIANG CROVELLA", "RON CYTRON", "WILLIAM J. DALLY", "B
 RUCE S. 
 DAVIE", "UMESH DAYAL", "THOMAS A. DEFANTI", "JAMES W. DEMMEL", "DAVID J. DEWITT", "IHOMAS G. DIETTERICH", "DAVID DILL", "CHRISTOPHE DIOT Y", "D. DOLEV", "J DONGARRA", "WERNER EBELING", "L. MIGUEL ENCARNACAO", "DAVID EPPSTEIN", "DEBORAH ESTRIN", "RON FAGIN", "HENRY S. FARBER", "RICHARD J. FATEMAN", "J. FEIGENBAUM", "MATTHISS FELLEISEN", "KENNETH FORBUS", "LANCE J. FORTNOW", "HAROLD GABOW", "ZVI GALIL", "DENNIS GANNON", "M. R. GAREY", "PROF CARLO GHEZZI", "DAVID K. GIFFORD", "MATTHEW L. GINSBERG", "MICHEL GOEMANS", "M. T. GOODRICH", "ARNAUD GOTLIEB", "M. GOVINDARAJU", "W. D. GROPP", "L. GROSZ", "L. GUIBAS", "COMMUNICATED Y. GUREVICH", "JOHN V. GUTTAG", "BRENT HAILPERN", "JOSEPH HALPERN", "D. HAREL", "JURIS HARTMANIS", "J. P. HEARN", "D. HECKERMAN", "PHILIP HEIDELBERGER", "L. HENDREN", "DANIEL HENNESSY", "MONIKA R. HENZINGER", "MAURICE HERLIHY", "GERARD J. HOLZMANN", "JOHN E. HOPCROFT", "JAMES J. HORNING", "J. P. HUBAUX", "N. IMMERMAN", "FARNAM JAHANIAN PRAKASH", "JOSEPH JAJA",
  "WILLIA
 M KAHAN", "RICH KALTOFEN", "HOWARD KARLOFF", "LYDIA KAVRAKI T", "MARIA M. KLAWE", "D. E. KNUTH", "PHOKION G. KOLAITIS", "J DONALD KOSSMANN", "G. KRAUT", "H. -P. KRIEGEL", "RICHARD E. LADNER", "B. LAMPSON", "A. VAN LAMSWEERDE", "LAURA F. LANDWEBER", "J. LAUNCHBURY", "EDWARD LAZOWSKA", "GIACOMO MAURIZIO LENZERINI", "N. LEVESON", "MARC LEVOY", "R. LIPTON", "H. LISKOV", "TODD LITWIN", "DAVID LOMET", "P. GEOFFREY LOWNEY", "DAVID MACQUEEN", "PETER S MAGNUSSON", "ERIC MAISEL", "DINESH MANOCHA BILL GARRETT", "MARGARET MARTONOSI", "LARRY MASINTER", "EDWARD J. MCCLUSKEY", "PAUL MCJONES", "E. DE MICHELI", "RITA G. MINKER", "PAUL V. MOCKAPETRIS", "RAJEEV MOTWANI", "D. MUNTZ", "MOORTHY MUTHUKRISHNAN", "ROGER NEEDHAM", "JURG NIEVERGELT", "PETER NORVIG", "D. NOTKIN", "DIANNE P. O'LEARY", "MARTIN ODERSKY", "OYEKUNLE OLUKOTUN", "SUSAN OWICKI", "DAVID A. PADUA", "KRISHNA V. PALEM", "P. PAPADIMITRIOU", "L. PARNAS", "BOAZ PATT", "Y PAUSCH (DISSERTATION ADVISOR", "LINDA PETZOLD", "PAVEL A. PEVZN
 ER", "HA
 MID PIRAHESH", "PROF AMIR PNUELI", "BRYAN PREAS", "FRANCO P. PREPARATA", "SAMUEL PULLEN", "RADA F. MIHALCEA", "KRITHI RAMAMRITHAM MEMBER", "B. RANDELL", "M. REINGOLD", "THOMAS REPS", "ARISTIDES A. G. REQUICHA", "JENNIFER L. REXFORD", "REINHARD RIEDL", "MARTIN C. RINARD", "MARK ROSENBLUM", "DANIEL ROSENKRANTZ", "KRISHAN K. SABNANI", "S. SAHNI", "DAVID SALESIN", "GERARD SALTON", "AMHED SAMEH", "LARRY SAMUELSON", "ANDERS B. SANDHOLM", "RAVINDERPAL SINGH SANDHU", "MAHADEV SATYANARAYANAN", "RICHARD D. SCHLICHTING", "JULIA A. SCHNABEL", "ROBERT SEDGEWICK", "CARLO H. SEQUIN", "M. SHARIR S", "SCOTT SHENKER (XEROX", "D. B. SHMOYS", "KUMAR PLAISANT SHNEIDERMAN", "R. K. SHYAMASUNDAR", "M. SNIR", "MARY LOU SOFFA", "DIANE L. SOUVAINE", "DANIEL SPIELMAN", "MARK S. SQUILLANTE", "JACK STANKOVIC", "RICHARD SQUIER STEIGLITZ", "ERIK S. STEINMETZ", "MICHAEL K. STENSTROM", "EDGARDO STOCKMEYER", "UBELL MICHAEL STONEBRAKER", "BJARNE STROUSTRUP", "ER I. SUCIU", "M. SUDAN", "RICHARD SZELISKI", "S. T
 UCKER TA
 FT", "A. S. TANENBAUM", "G. TARDOS", "D. TERZOPOULOS", "SANJEEV THACKER", "RAMOHAN A. THEKKATH", "MIKKEL THORUP", "MARTIN TOMPA", "KOJI TORII", "J. TRAUB", "DEAN M. TULLSEN", "ANNA R. KARLIN ELI UPFAL", "MATHER AMIN VAHDAT", "GEORGE VARGHESE", "V. VIANU", "UZI VISHKIN", "JEREY S. VITTER", "PHILIP WADLER AVAYA LABS", "M. P. WAND", "ROY WANT", "HENRY WASSERMAN", "BEN WEGBREIT", "EDWARD J. WEGMAN", "DANIEL S. WELD", "EMO WELZL", "ELAINE J. WEYUKER", "TURNER WHITTED", "PETER WIDMAYER", "BRENDA WIEDERHOLD", "M. WILKES", "ALLAN R. WILKS", "SHMUEL WINOGRAD", "KENT E. SEAMONS MARIANNE WINSLETT", "H. WITTEN", "M. YANNAKAKIS", "AKI YONEZAWA", "FROM PAMELA ZAVE", "B. ZDONIK"};
        // WOS
        String[] jmenaWOS_ACMFell = {"ABADI, M", "ABITEBOUL, S", "ABOWD, GD", "ABRAHAM, JA", "ADVE, S", "AGARWAL, A", "AGARWAL, PK", "AGRAWAL, D", "AGRAWAL, P", "AGRAWAL, VD", "AHUJA, N", "AKYILDIZ, IF", "ALLEN, FE", "ALLENDER, E", "ALUR, R", "ALVISI, L", "AMMAR, MH", "ANDERSON, TE", "ANDREWS, GR", "APPEL, AW", "ARORA, S", "ASANO, T", "ATALLAH, MJ", "ATTIYA, H", "AUSLANDER, M", "BABAOGLU, O", "BACON, DF", "BAHL, V", "BAJAJ, CL", "BALAKRISHNAN, H", "BANERJEE, P", "BARR, AH", "BARROSO, LA", "BASILI, VR", "BATE, RR", "BATCHER, KE", "BEERI, C", "BELL, CG", "BERGER, B", "BERGHEL, H", "BERMAN, F", "BERNSTEIN, L", "BERNSTEIN, PA", "BERTINO, E", "BHUYAN, LN", "BIERMANN, AW", "BIRMAN, KP", "BLASGEN, MW", "BLELLOCH, G", "BLUM, A", "BOCHMANN, GV", "BOKHARI, SH", "BOOCH, G", "BORG, A", "BORRIELLO, G", "BOSE, B", "BRADEN, RT", "BRACHMAN, RJ", "BREITBART, YJ", "BRENT, RP", "BREWER, EA", "BRODER, AZ", "BROOKS, FP", "BROWNE, JC", "BRYANT, RE", "BUBENKO, JA", "BUNEMAN, P", "BURGER, D", "BUXTO
 N, W", "
 CAI, JY", "CARD, SK", "CARDELLI, L", "CAREY, JM", "CARROLL, JM", "CLARK, DD", "CLARKE, EM", "CLARKE, LA", "CLARKSON, KL", "COFFMAN, EG", "COHEN, MF", "COLE, RJ", "COLLINS, GE", "COMER, DE", "CONDON, A", "CONG, J", "CONSTABLE, RL", "CONSTANTINE, LL", "COOK, P", "COOK, SA", "COOPER, KD", "CORBATO, FJ", "CROFT, WB", "CROVELLA, M", "CULLER, DE", "CYTRON, RK", "DAHLIN, MD", "DALLY, WJ", "DAVIDSON, JW", "DAVIDSON, SB", "DAVIS, GB", "DAYAL, U", "DEFANTI, T", "DEMILLO, RA", "DEMMEL, JW", "DENG, X", "DENNING, DE", "DENNING, PJ", "DEO, N", "DEWITT, DJ", "DIETTERICH, TG", "DIJKSTRA, EW", "DIOT, C", "DOBKIN, DP", "DOLEV, D", "DONALD, BR", "DONGARRA, J", "DOWNEY, RG", "DUBOIS, M", "DUMAIS, ST", "EBELING, C", "ECKERT, JP", "EGGERS, SJ", "EL ABBADI, A", "ELLIS, CA", "ELLIS, CS", "EMER, J", "ENCARNACAO, JL", "ENGEL, GL", "EPPSTEIN, D", "ERICKSON, T", "ESTRIN, DL", "FAGIN, R", "FALOUTSOS, C", "FARBER, DJ", "FATEMAN, R", "FAYYAD, U", "FEIGENBAUM, J", "FELDMAN, SI", "FELLEISEN, M", "FELTEN, EW
 ", "FENG
 , TY", "FERRANTE, J", "FERRARI, D", "FISCHER, G", "FISCHER, MJ", "FLYNN, MJ", "FORBUS, KD", "FOX, GC", "FRANKLIN, MJ", "FREEMAN, H", "FREEMAN, PA", "FRIEDER, O", "FUCHS, H", "FUCHS, WK", "GABOW, HN", "GALIL, Z", "GALLER, BA", "GAO, GR", "GARCIA-LUNA-ACEVES, JJ", "GARCIA-MOLINA, H", "GEAR, CW", "GELENBE, E", "GHEZZI, C", "GIBBONS, PB", "GIFFORD, DK", "GILES, CL", "GINSBERG, M", "GLASS, RL", "GOEMANS, M", "GOLDBERG, AV", "GOODMAN, J", "GOODRICH, MT", "GOTTLOB, G", "GOVINDAN, R", "GOVINDARAJU, V", "GOYAL, A", "GRAHAM, RL", "GRAHAM, SL", "GRAY, J", "GREENBERG, A", "GREENBERG, DP", "GRIES, D", "GROPP, WD", "GROSZ, BJ", "GUERIN, R", "GUIBAS, LJ", "GUO, B", "GUPTA, G", "GUPTA, R", "GUREVICH, Y", "GUTTAG, J", "GUZMAN, A", "HAAS, LM", "HALEVY, A", "HALL, W", "HANRAHAN, P", "HANSON, VL", "HAREL, D", "HARRISON, M", "HARROLD, MJ", "HART, PE", "HARTMANIS, J", "HAYES, JP", "HEATH, MT", "HECKERMAN, D", "HEIDELBERGER, P", "HELLERSTEIN, JM", "HENNESSY, JL", "HENZINGER, TA", "HIGHLAND, HJ", "
 HILL, MD
 ", "HILLIS, D", "HOFFMAN, LJ", "HOLZMANN, GJ", "HOPPE, H", "HUBAUX, JP", "HUDAK, P", "HULL, R", "HUMPHREY, WS", "HUSKEY, HD", "HUTTENLOCHER, DP", "HWU, WM", "CHAMBERLIN, DD", "CHANDRASEKARAN, B", "CHAUDHURI, S", "CHAYES, J", "CHAZELLE, B", "CHEN, MS", "CHEN, PM", "CHEN, PP", "CHIEN, AA", "CHLAMTAC, I", "CHOUDHARY, A", "IBARAKI, T", "IBARRA, OH", "IMMERMAN, N", "IOANNIDIS, YE", "IRWIN, MJ", "IYENGAR, S", "IYER, RK", "JAFFE, J", "JAGADISH, HV", "JAHANIAN, F", "JAIN, AK", "JAIN, R", "JAJA, JF", "JENSEN, CS", "JHA, NK", "JOHNSON, DS", "JONES, AK", "JONES, CB", "JONES, ND", "JORDAN, MI", "JOSHI, AK", "JOUPPI, NP", "KAASHOEK, F", "KAHAN, W", "KAHN, RE", "KANADE, T", "KANDEL, A", "KANG, SM", "KAPLAN, RM", "KARGER, DR", "KARP, RM", "KATZ, RH", "KAUFMAN, AE", "KAVRAKI, LE", "KECKLER, SW", "KEDEM, Z", "KELLOGG, WA", "KEMMERER, RA", "KIEBURTZ, RB", "KIESLER, S", "KIRKPATRICK, S", "KLAWE, M", "KLEIN, PN", "KLEINROCK, L", "KNUTH, DE", "KOLAITIS, PG", "KONSTAN, JA", "KORTH, HF", "KOSARAJU
 , SR", "
 KOSSMANN, D", "KOWALSKI, RA", "KOZEN, D", "KRAMER, J", "KRAUT, RE", "KRIEGEL, HP", "KUCK, DJ", "KUROSE, JF", "KURZWEIL, R", "LADNER, RE", "LAIRD, JE", "LAKSHMAN, TV", "LAKSHMIVARAHAN, S", "LAM, MS", "LAM, SS", "LAMPSON, BW", "LANDAU, S", "LARUS, JR", "LAUNCHBURY, J", "LAWSON, HW", "LEHMAN, MM", "LEISERSON, CE", "LENZERINI, M", "LEVESON, NG", "LEVOY, M", "LEVY, HM", "LEWIS, PM", "LINDSAY, BG", "LIPTON, RJ", "LITWIN, W", "LORIE, RA", "LOVELAND, DW", "LUI, JCS", "LYNCH, NA", "LYON, RF", "MAGNUSSON, PS", "MAIER, D", "MAISEL, H", "MALIK, J", "MALKHI, D", "MANNA, Z", "MANOCHA, D", "MARTONOSI, M", "MARZULLO, K", "MATIAS, Y", "MATSUOKA, S", "MAX, N", "MC CARTHY, J", "MCCLUSKEY, EJ", "MCKEOWN, K", "MCKEOWN, N", "MCKINLEY, KS", "MEHLHORN, K", "MENASCE, DA", "MEYER, AR", "MEYER, B", "MILLER, BP", "MILLER, GL", "MILLER, RE", "MILLER, RJ", "MILLS, DL", "MINKER, J", "MISHRA, B", "MISRA, J", "MITCHELL, J", "MITCHELL, JC", "MOGUL, JC", "MOHAN, C", "MOONEY, R", "MOORE, JS", "MORAN, TP", "MOR
 RIS, JH"
 , "MOSES, J", "MOSS, JEB", "MOTWANI, R", "MUKHERJEE, S", "MUNRO, JI", "MUNTZ, RR", "MUTHUKRISHNAN, S", "MYERS, BA", "MYERS, EW", "NAUGHTON, JF", "NEEDHAM, RM", "NEUMANN, PG", "NEWELL, AF", "NICOL, DM", "NIEVERGELT, J", "NISHIZEKI, T", "NORMAN, DA", "NORVIG, P", "ODERSKY, M", "O'LEARY, DP", "OLSEN, DR", "OLSON, GM", "OLSON, JS", "OOI, BC", "OSTERWEIL, LJ", "OUSTERHOUT, JK", "OZSU, MT", "PADUA, D", "PACH, J", "PALEM, K", "PANCAKE, CM", "PAPADIMITRIOU, C", "PARKER, DB", "PARNAS, DL", "PARTRIDGE, C", "PATEL, JH", "PATT, YN", "PATTERSON, DA", "PAULSON, LC", "PAUSCH, R", "PAXSON, V", "PEREIRA, FC", "PERROTT, RH", "PETERSON, LL", "PETZOLD, L", "PEVZNER, P", "PIPPENGER, N", "PIRAHESH, H", "PNUELI, A", "POLLACK, ME", "PRADHAN, DK", "PRASANNA, VK", "PREAS, BT", "PREPARATA, FP", "PULLEN, JM", "RAGHAVAN, P", "RALSTON, A", "RAMAKRISHNAN, R", "RAMAMRITHAM, K", "RANDELL, B", "RAU, BR", "REED, DA", "REIF, JH", "REINGOLD, EM", "REITER, MK", "REITER, R", "REPS, T", "REQUICHA, AAG", "REXFORD, 
 J", "REY
 NOLDS, JC", "RICE, JR", "RIEDL, JT", "RICHARDS, JT", "RINARD, M", "RIVEST, RL", "ROMBACH, D", "ROSE, JS", "ROSENBERG, AL", "ROSENBLUM, DS", "ROSENBLUM, M", "ROSENFELD, A", "ROSENKRANTZ, DJ", "ROTH, D", "ROUSSOPOULOS, N", "ROWE, LA", "RUSSELL, SJ", "RUTENBAR, RA", "RYDER, BG", "SABNANI, KK", "SAHNI, SK", "SALESIN, DH", "SALTON, G", "SAMET, H", "SAMMET, JE", "SAMUELSON, P", "SANDERS, WH", "SANDHOLM, T", "SANDHU, RS", "SARKAR, V", "SATYANARAYANAN, M", "SAVAGE, JE", "SAVAGE, S", "SCOTT, ML", "SELINGER, P", "SELMAN, AL", "SELTZER, M", "SEQUIN, CH", "SETHI, R", "SEVCIK, KC", "SHA, L", "SHARIR, M", "SHIN, KG", "SHNEIDERMAN, B", "SHYAMASUNDAR, RK", "SCHANTZ, R", "SCHEK, HJ", "SCHLICHTING, RD", "SCHNABEL, RB", "SCHNEIDER, FB", "SIEGEL, HJ", "SIEWIOREK, D", "SILBERSCHATZ, A", "SIMON, HA", "SINGHAL, A", "SLUTZ, DR", "SMITH, AJ", "SMITH, BJ", "SNIR, M", "SNODGRASS, RT", "SNYDER, L", "SOFFA, ML", "SOHI, GS", "SPAFFORD, EH", "SPIELMAN, DA", "SQUILLANTE, MS", "SRIVASTAVA, D", "STANKOVIC, J
 A", "STE
 ARNS, RE", "STEELE, GL", "STEINMETZ, R", "STENSTROM, P", "STOCKMEYER, L", "STONEBRAKER, M", "STROUSTRUP, B", "SUCIU, D", "SUDAN, M", "SUPPES, P", "SURI, S", "SUZUKI, N", "SZELISKI, R", "TANENBAUM, AS", "TARDOS, E", "TARJAN, RE", "TAYLOR, RN", "TENG, SH", "TERZOPOULOS, D", "THOMAS, DE", "THORUP, M", "TOMPA, FW", "TORII, K", "TORRELLAS, J", "TOWSLEY, DF", "TUCKER, AB", "TULLSEN, DM", "TURNER, AJ", "UPFAL, E", "VAHDAT, A", "VALERO, M", "VAN DAM, A", "VAN RENESSE, R", "VAN RIJSBERGEN, CJ", "VARGHESE, G", "VASSILIADIS, S", "VEMURI, BC", "VERNON, MK", "VETTERLI, M", "VISHKIN, U", "VITTER, JS", "WADLER, P", "WAH, BW", "WALLACE, CS", "WALTZ, DL", "WAND, M", "WANT, R", "WARNOCK, J", "WARREN, DS", "WASSERMAN, AI", "WEGMAN, M", "WEGNER, P", "WEIKUM, G", "WEISS, EA", "WELD, DS", "WELLMAN, MP", "WELZL, E", "WEYUKER, EJ", "WHANG, KY", "WIDMAYER, P", "WIDOM, J", "WIEDERHOLD, G", "WILENSKY, R", "WILHELM, R", "WILKES, J", "WILKES, MV", "WILLINGER, W", "WING, JM", "WINOGRAD, T", "WINSLETT, M"
 , "WISE,
  DS", "WITTEN, I", "WOLF, AL", "WOLF, M", "WOLFSON, O", "WOOD, DA", "WULF, WA", "YANNAKAKIS, M", "YAO, AC", "YONEZAWA, A", "ZADECK, FK", "ZADEH, LA", "ZAVE, P", "ZELLER, A", "ZHAI, S"};
        // zarazeni do mapy jmen
        mapaJmen.put("ACMFELLOWSDBLP", jmenaDBLP_ACMFell);
        mapaJmen.put("ACMFELLOWSCITESEER", jmenaCiteSeer_ACMFell);
        mapaJmen.put("ACMFELLOWSWOS", jmenaWOS_ACMFell);

      // ISI Highly Cited
        // DBLP
        String[] jmenaDBLP_ISIHC = {"SERGE ABITEBOUL", "EDWARD H. ADELSON", "IAN F. AKYILDIZ", "RAJEEV ALUR", "KRZYSZTOF R. APT", "JACK J. BAROUDI", "TERENCE M. BARRON", "VICTOR R. BASILI", "CATRIEL BEERI", "JAN A. BERGSTRA", "LAXMI N. BHUYAN", "ANDREW BIRRELL", "SHAHID H. BOKHARI", "JEHOSHUA BRUCK", "JOHN CHANDY", "BERNARD CHAZELLE", "IMRICH CHLAMTAC", "KAREL CULIK II", "DANNY DOLEV", "JACK DONGARRA", "CYNTHIA DWORK", "DEREK L. EAGER", "HERBERT EDELSBRUNNER", "ANDRZEJ EHRENFEUCHT", "E. ALLEN EMERSON", "RONALD FAGIN", "JEANNE FERRANTE", "PHILIPPE FLAJOLET", "GREG N. FREDERICKSON", "HAROLD N. GABOW", "DAVID GELERNTER", "ODED GOLDREICH", "JUDY GOLDSMITH", "MICHAEL GOLDWASSER", "JOSEPH Y. HALPERN", "SVEN HAMMARLING", "DAVID HAUSSLER", "MONIKA RAUCH HENZINGER", "DORIT S. HOCHBAUM", "OSCAR H. IBARRA", "NEIL IMMERMAN", "THOMAS JOHNSSON", "TADAO KASAMI", "CHRIS F. KEMERER", "JAMES E. KIRKPATRICK", "JAN WILLEM KLOP", "DEXTER KOZEN", "LESLIE LAMPORT", "WENDY G. LEHNERT", "NANCY G. LEV
 ESON", "
 MARC LEVOY", "NATHAN LINIAL", "BARBARA LISKOV", "UDI MANBER", "NIMROD MEGIDDO", "KURT MEHLHORN", "DAVID NASSIMI", "MARK H. OVERMARS", "CAROLINE PAXSON", "HENRI PRADE", "FRANCO P. PREPARATA", "CHARLES RACKOFF", "KRITHI RAMAMRITHAM", "JOHN H. REIF", "THOMAS W. REPS", "ARISTIDES A. G. REQUICHA", "JORMA RISSANEN", "WALTER L. RUZZO", "GERARD SALTON", "ROBERT E. SCHAPIRE", "KENNETH C. SEVCIK", "MICHA SHARIR", "YOSSI SHILOACH", "BEN SHNEIDERMAN", "PETER W. SHOR", "DANIEL DOMINIC SLEATOR", "LARRY J. STOCKMEYER", "JORGE STOLFI", "ROBERTO TAMASSIA", "ROBERT ENDRE TARJAN", "SAM TOUEG", "LESLIE G. VALIANT", "MOSHE Y. VARDI", "UZI VISHKIN", "JEFFREY SCOTT VITTER", "JEAN VUILLEMIN", "ELAINE J. WEYUKER", "AVI WIGDERSON", "IAN H. WITTEN", "MIHALIS YANNAKAKIS", "JOHN ZAHORJAN"};
        // CiteSeer
        String[] jmenaCiteSeer_ISIHC = {"B. AAZHANG", "MIGUEL ABADI", "S. ABITEBOUL", "J. ADELSON", "M. AHMADI", "MOHAMED-SLIM ALOUINI", "RAJEEV ALUR", "MIKHAIL ATALLAH", "TOR M. AULIN", "G. BALBO", "ANDREW R. BARRON", "V. BASILI", "CATRIEL BEERI", "JAN A. BERGSTRA", "LAXMI N. BHUYAN", "E. BIGLIERI", "HANS L. BODLAENDER T", "ROBERT C. BOLLES", "GILLES BRASSARD", "MANFRED BROY", "JEHOSHUA BRUCK", "DAVID M. J. CALDERBANK", "BERNARD CHAZELLE", "DAVID CHERITON", "JOHN G. CLEARY", "DON COPPERSMITH", "B. COURCELLE", "WILLIAM CROFT", "JEREMY DU CROZ", "KAREL CULIK II", "WILLIAM J. DALLY", "INGRID DAUBECHIES", "DAVID DILL", "DARIUSH DIVSALAR", "D. DOLEV", "J DONGARRA", "ES DAVID L. DONOHO", "DAVID DUFF", "DEREK EAGER", "READER HERBERT EDELSBRUNNER", "JOERI ENGELFRIET", "RON FAGIN", "N. FARVARDIN", "P. FLAJOLET", "G. J. FOSCHINI", "FOSSORIER ZIXIANG", "M. L. FREDMAN", "HAROLD GABOW", "ZVI GALIL", "R. GALLAGER", "DAVID GELERNTER", "SHA GOLDWASSER", "BENJAMIN GREENSTEIN", "JOACHIM HAGEN
 AUER", "
 JOSEPH HALPERN", "SVEN J. HAMMARLING", "D. HAREL", "BABAK HASSIBI", "DANIEL HENNESSY", "MONIKA R. HENZINGER", "MICHAEL G. HLUCHYJ", "DORIT S. HOCHBAUM", "BERTRAND HOCHWALD SWELDENS", "N. IMMERMAN", "HAMID JAFARKHANI", "THOMAS KAILATH", "EDWARD J. KANSA", "N. KARMARKAR", "CHRIS F. KEMERER", "ALEX KRUSKAL", "J. NICHOLAS LANEMAN", "EDWARD LAZOWSKA", "WENDY G. LEHNERT", "KHALED BEN LETAIEF", "N. LEVESON", "D. LEVIATAN", "MARC LEVOY", "N. LINIAL", "H. LISKOV", "MICHAEL LUBY", "DAVID M. LUCANTONI", "UPAMANYU MADHOW", "ST EPHANE MALLAT", "DIRECTOR UDI MANBER", "LAURENT MARSAN", "N. F. MAXEMCHUK", "ROBERT MCELIECE", "NIMROD MEGIDDO", "NERI MERHAV T", "SILVIO MICALI", "SUVO MITTRA", "N. MOENECLAEY", "GUIDO MONTORSI", "M. NAGHSHINEH", "DAVID NASSIMI", "M. H. OVERMARS", "P. PAPADIMITRIOU", "JOACHIM PARROW", "AROGYSWAMI PAULRAJ", "PROF AMIR PNUELI", "ANDREAS POLYDOROS", "FRANCO P. PREPARATA", "CHARLES RACKOFF", "RAGHAVENDRA DONAMUKKALA", "KRITHI RAMAMRITHAM MEMBER", "SUNEETA RAMASWAMI",
  "D. RAY
 CHAUDHURI", "THOMAS REPS", "ARISTIDES A. G. REQUICHA", "ROSCOE GILES", "RUZZO W. L", "S. SAHNI", "GERARD SALTON", "TALYA SALZ", "MAMORU SAWAHASHI", "R. SCHAPIRE", "DANIEL H. SCHAUBERT", "SHLOMO SHAMAI (SHITZ", "M. SHARIR S", "KUMAR PLAISANT SHNEIDERMAN", "PETER W. SHOR", "SISTLA CLEMENT YU", "TYCHO SLEATOR", "M. SNIR", "SOLOWAY EHRHCH", "JACK STANKOVIC", "EDGARDO STOCKMEYER", "JORGE STOLFI", "ESWARAN SUBRAHMANIAN", "M. SUDAN", "GIORGIO TARICCO", "VAHID TAROKH", "T. TEITELBAUM", "ER VARDY", "UZI VISHKIN", "ANDREW J VITERBI", "EMANUELE VITERBO", "JEREY S. VITTER", "JEAN E. VUILLEMIN", "MANFRED WARMUTH", "B. WELSCH", "ELAINE J. WEYUKER", "STEVEN WILTON", "H. WITTEN", "M. YANNAKAKIS", "JOHN ZAHORJAN"};
        // WOS
        String[] jmenaWOS_ISIHC = {"AAZHANG, B", "ABADI, M", "ABITEBOUL, S", "ABRAHAM, JA", "ACAMPORA, AS", "ADACHI, F", "ADELSON, EH", "AGRAWAL, DP", "AHMADI, H", "AKYILDIZ, IF", "ALLEN, JF", "ALON, N", "ALOUINI, MS", "ALUR, R", "APT, KR", "ATALLAH, MJ", "BALBO, G", "BARRON, AR", "BASILI, VR", "BEAULIEU, NC", "BEERI, C", "BENEDETTO, S", "BENNETT, CH", "BERGSTRA, JA", "BERNSTEIN, PA", "BERROU, C", "BHARGAVA, VK", "BHUYAN, LN", "BIGLIERI, E", "BINNIG, GK", "BLUM, M", "BODLAENDER, HL", "BOKHARI, SH", "BOLLES, RC", "BOOK, RV", "BRASSARD, G", "BRUCK, J", "BRYANT, RE", "BUCKLEY, C", "CAIRE, G", "CALDERBANK, AR", "CIOFFI, JM", "CLARKE, EM", "CLEARY, JG", "COLE, R", "CONWAY, JH", "COPPERSMITH, D", "COSTELLO, DJ", "COURCELLE, B", "COVER, TM", "CROFT, WB", "CRUZ, RL", "CULIK, K", "CURTIS, B", "DALLY, WJ", "DAUBECHIES, I", "DENNIS, JE", "DILL, DL", "DOLEV, D", "DONGARRA, J", "DONOHO, DL", "DUBOIS, D", "DUEL-HALLEN, A", "DUFF, IS", "DWORK, C", "EAGER, DL", "EDELSBRUNNER, H", "EHRENFEUCH
 T, A", "
 EMERSON, EA", "ENGELFRIET, J", "FAGIN, R", "FARVARDIN, N", "FERRANTE, J", "FERRARI, D", "FISCHER, MJ", "FLAJOLET, P", "FLOYD, S", "FORNEY, GD", "FOSCHINI, GJ", "FOSSORIER, MPC", "FOSTER, I", "FREDERICKSON, GN", "FREDMAN, ML", "GABOW, HN", "GALIL, Z", "GALLAGER, RG", "GARCIA-MOLINA, H", "GAVISH, B", "GAY, DM", "GELERNTER, D", "GERLA, M", "GERSHO, A", "GIANNAKIS, GB", "GIRARD, JY", "GLISSON, AW", "GOLDREICH, O", "GOLDSMITH, AJ", "GOLDWASSER, S", "GOODMAN, DJ", "GOTTLOB, G", "GRAY, RM", "GREENSTEIN, LJ", "GUERIN, R", "HAGENAUER, J", "HALPERN, JY", "HAMMARLING, S", "HAREL, D", "HASSIBI, B", "HASTAD, J", "HAUSSLER, D", "HAYES, JP", "HENNESSY, JL", "HENNESSY, M", "HENZINGER, TA", "HOARE, CAR", "HOCHBAUM, DS", "HOCHWALD, BM", "HONIG, ML", "HUI, JY", "CHANDRA, AK", "CHANDY, KM", "CHAZELLE, B", "CHLAMTAC, I", "CHOI, BK", "IBARRA, OH", "IMMERMAN, N", "IVES, B", "JAFARKHANI, H", "JAIN, AK", "JENNINGS, NR", "JOHNSSON, SL", "KAHN, JM", "KAILATH, T", "KANSA, EJ", "KARMARKAR, NK", "KARP, R
 M", "KAS
 AMI, T", "KEMERER, CF", "KESSELMAN, C", "KIRKPATRICK, DG", "KLEINROCK, L", "KLOP, JW", "KOHNO, R", "KOMLOS, J", "KRUSKAL, CP", "KUMAR, PV", "KUNG, HT", "LAMPORT, L", "LAZOWSKA, ED", "LEHNERT, JS", "LEVESON, NG", "LEVOY, M", "LINIAL, N", "LIU, KJR", "LOVASZ, L", "LUBY, M", "LYNCH, NA", "MADHOW, U", "MAIER, D", "MALLAT, SG", "MANBER, U", "MARSAN, MA", "MAXEMCHUK, NF", "MCELIECE, RJ", "MEGIDDO, N", "MEHLHORN, K", "MERHAV, N", "MICALI, S", "MILNER, R", "MILSTEIN, LB", "MISRA, J", "MITRA, D", "MITTRA, R", "MOENECLAEY, M", "MONTANARI, U", "MONTORSI, G", "MORAN, S", "MUKHERJEE, B", "NAGHSHINEH, M", "NASSIMI, D", "NEAL, RM", "NI, LM", "OVERMARS, MH", "PAIGE, CC", "PAPADIMITRIOU, CH", "PARROW, J", "PASUPATHY, S", "PATEL, JH", "PAUN, G", "PAXSON, V", "PELEG, D", "PNUELI, A", "POOR, HV", "PRADE, H", "PRADHAN, DK", "PREPARATA, FP", "PURSLEY, MB", "RACKOFF, C", "RAGHAVAN, P", "RAGHAVENDRA, CS", "RAMAMRITHAM, K", "RAMASWAMI, R", "RAYCHAUDHURI, D", "REIF, JH", "REPS, T", "REQUICHA, AAG", "
 RICHARDS
 ON, TJ", "RISSANEN, JJ", "RIVEST, RL", "ROSCOE, AW", "ROSENBERG, AL", "ROZENBERG, G", "RUDOLPH, L", "RUZZO, WL", "SAAD, Y", "SAHNI, S", "SALTON, G", "SALZ, J", "SARKAR, TK", "SAWAHASHI, M", "SESHADRI, N", "SEVCIK, KC", "SHAMAI, S", "SHARIR, M", "SHIN, KG", "SHNEIDERMAN, B", "SHOR, PW", "SCHAPIRE, RE", "SCHNEIDER, FB", "SIEGEL, HJ", "SIMON, MK", "SISTLA, AP", "SLEATOR, D", "SLOANE, NJA", "SNIR, M", "SOLOWAY, E", "STANKOVIC, JA", "STOCKMEYER, LJ", "STOLFI, J", "STOUT, QF", "SUBRAHMANIAN, VS", "SUDAN, M", "SUNDBERG, CEW", "TAMASSIA, R", "TARICCO, G", "TARJAN, RE", "TAROKH, V", "TEITELBAUM, T", "TOUEG, S", "TOWSLEY, D", "TRIVEDI, KS", "TSE, DNC", "TURNER, JS", "VALENZUELA, RA", "VALIANT, LG", "VARANASI, MK", "VARDI, MY", "VARDY, A", "VAZIRANI, VV", "VERDU, S", "VISHKIN, U", "VITERBI, AM", "VITERBO, E", "VITTER, JS", "VUILLEMIN, J", "WAH, BW", "WARMUTH, MK", "WEI, VKW", "WEISER, M", "WEYUKER, EJ", "WHITT, W", "WIGDERSON, A", "WILLINGER, W", "WING, JM", "WITTEN, IH", "WOLF, JK", "
 YAGER, R
 R", "YANNAKAKIS, M", "ZADEH, LA", "ZAHORJAN, J"};
        // zarazeni do mapy jmen
        mapaJmen.put("ISIDBLP", jmenaDBLP_ISIHC);
        mapaJmen.put("ISICITESEER", jmenaCiteSeer_ISIHC);
        mapaJmen.put("ISIWOS", jmenaWOS_ISIHC);

      // ACM Turing Award
        // DBLP
        String[] jmenaDBLP_Turing = {"LEONARD M. ADLEMAN", "JOHN COCKE", "E. ALLEN EMERSON", "JOAN FEIGENBAUM", "JURIS HARTMANIS", "JOHN E. HOPCROFT", "BARBARA LISKOV", "PETER NAUR", "DONALD PERLIS", "JOSEPH SIFAKIS", "ROBERT ENDRE TARJAN", "LESLIE G. VALIANT"};
        // CiteSeer
        String[] jmenaCiteSeer_Turing = {"M. ADLEMAN", "C. R. BACHMAN", "BENJAMIN T. BACKUS", "JOHN COCKE STEPHEN", "J. FEIGENBAUM", "JURIS HARTMANIS", "JOHN E. HOPCROFT", "WILLIAM KAHAN", "D. E. KNUTH", "B. LAMPSON", "H. LISKOV", "ERLING NYGAARD", "PROF AMIR PNUELI", "DAN RABIN", "SANJEEV THACKER", "M. WILKES"};
        // CiteSeer
        String[] jmenaWOS_Turing = {"ADLEMAN, LM ", "ALLEN, FE", "BACKUS, J ", "BLUM, M", "BROOKS, FP", "CERF, VG", "CLARKE, EM", "COCKE, J ", "COOK, SA", "CORBATO, FJ ", "DIJKSTRA, EW", "EMERSON, EA", "FEIGENBAUM, EA", "HARTMANIS, J ", "HOARE, CAR", "IVERSON, KE", "KAHAN, W", "KAHN, RE", "KARP, RM", "KNUTH, DE", "LAMPSON, BW", "LISKOV, B", "MILNER, R", "MINSKY, M", "NEWELL, A", "PNUELI, A", "RABIN, MO", "RITCHIE, DM", "RIVEST, RL", "SHAMIR, A", "SIFAKIS, J ", "SIMON, HA", "STEARNS, RE", "SUTHERLAND, I", "TARJAN, RE", "VALIANT, LG", "WILKES, MV", "WIRTH, N", "YAO, AC"};
        // zarazeni do mapy jmen
        mapaJmen.put("ACMTURINGDBLP", jmenaDBLP_Turing);
        mapaJmen.put("ACMTURINGCITESEER", jmenaCiteSeer_Turing);
        mapaJmen.put("ACMTURINGWOS", jmenaWOS_Turing);


      /// vraceni pozadovaneho
        if(!typCeny.equalsIgnoreCase("VSE")){ // jedna pozadanovana cena
            String pozad = (typCeny+typXML).toUpperCase();
            System.out.println("Nacitam jmena ocenenych autoru: "+pozad);
            if(mapaJmen.containsKey(pozad)){
                return mapaJmen.get(pozad);
            } else {
                return null;
            }
        } else { // vsechny ceny
            String ceny[] = {"CoddA", "ACMFellows", "ISI", "ACMTuring"};
            HashSet<String> setJmenVysl = new HashSet<String>();
            for(String typC: ceny){
                String pozad = (typC+typXML).toUpperCase();
                System.out.println("Nacitam jmena ocenenych autoru: "+pozad);
                if(mapaJmen.containsKey(pozad)){
                    System.out.println("   jmen: "+mapaJmen.get(pozad).length);
                    setJmenVysl.addAll(Arrays.asList(mapaJmen.get(pozad)));
                } else {
                    System.out.println("SPATNE NACTENA JMENA CENY: "+pozad);
                }
            }
            System.out.println("Celkem unikatnich jmen ocenenych autoru: "+setJmenVysl.size());
            return setJmenVysl.toArray(new String[setJmenVysl.size()]);
        }
    }