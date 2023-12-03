# Tema 1 PA 2023

Made by Andrei Mărunțiș

> **Nota**: Acest readme este scris in markdown, deschide-l cu un editor specializat pentru o citire mai usoara.

## Problema 1 - Supercomputer

Solutia la aceasta problema are la baza algoritmul lui Khan cu BFS oferit de echipa de PA ca solutie pentru laboratorul 6. Am modificat algoritmul utilizand idei preluate de [aici](https://www.geeksforgeeks.org/0-1-bfs-shortest-path-binary-graph/).

In esenta, pentru a rezolva problema doar aplic algoritmul lui Khan cu urmatorul twist: folosesc un [deque](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html) pentru a putea baga elemente in coada prin ambele capete, cu intentia ca nodurile care nu necesita schimbarea datelor sa fie in fata, si celelalte, in spate. Astfel, algoritmul nu va alege niciodata noduri care schimba pachetul de date din memorie daca mai sunt noduri disponibile care folosesc acele date.

In rest, algoritmul functioneaza astfel:

- Parcurg toate nodurile si calculez gradul lor intern (nr de muchii adiacente pe acel nod)
- Aleg un set de date cu care sa incep sortarea topologica (1 sau 2)
- Nodurile care au gradul intern 0 le adaug intr-o coada dupa regula descrisa mai sus (cele care au acelasi set de date precum cel cu care incep in fata, celelalte in spate)
- Parcurg fiecare nod din coada creata la pasul anterior, astfel:
    - scot nodul din coada
    - verific daca nodul din coada necesita celalalt pachet de date decat cel activ in memorie
        - daca da, atunci schimb setul de date din memorie si contorizez aceasta modificare (caci asta este solutia problemei)
    - parcurg fiecare vecin al nodului
        - scad gradul intern al vecinului cu o unitate
        - verific daca acum vecinul are gradul intern 0; daca da, il adaug in coada dupa regula descrisa mai sus (cele care au acelasi set de date precum cel din memorie in fata, celelalte in spate)
    - continui cu urmatorul nod din coada, pana cand aceasta se goleste

Grafurile de test nu au ciclii, astfel incat nu este nevoie sa verific acest aspect sau sa marchez in vreun fel nodurile deja parcurse.

Este nevoie sa aplic algoritmul de mai sus de 2 ori pe fiecare graf, ca sa testez ambele variante (cand incep cu 1, respectiv cand incep cu 0). Solutia problemei va fi valoarea mai mica dintre cele doua.
    
> Complexitatea solutiei: `O(N + E)`; N = nr de noduri, E = nr de muchii (complexitatea solutiei este complexitatea alg lui Khan, deoarece utilizarea cozii nu mareste in niciun fel complexitatea).

##  Problema 2 - Ferate

Soluția este inspirata de [aici](https://www.geeksforgeeks.org/minimum-edges-to-be-added-in-a-directed-graph-so-that-any-node-can-be-reachable-from-a-given-node/).

Folosesc mai multe parcurgeri DFS (si parcurgeri BFS ar merge) ale grafului ca sa gasesc nodurile accesibile, respectiv inaccesibile din graf, astfel:

- Realizez o parcurgere a grafului pornind din gara principala data de problema pentru a gasi ce noduri sunt accesibile deja, si le marchez ca atare
- Pentru fiecare dintre celelalte noduri ma intereseaza cate noduri inaccesibile direct de la sursa as putea accesa daca as construi un drum de la sursa la nodul respectiv; pentru a determina aceasta valoare realizez o parcurgere DFS pornind din fiecare nod nevizitat si tin minte numarul de noduri accesibile din fiecare nod
- Sortez descrescator lista creata la pasul anterior si folosesc o abordare greedy pentru a determina numarul minim de muchii ce trebuie adaugate: adaug muchie de la sursa catre nodul cu numar maxim de noduri care pot fi vizitate din el, marchez toate nodurile noi ca fiind vizitabile din sursa si continui algoritmul pana cand pot vizita toate nodurile.

> Complexitatea solutiei: `O(N^2 + E)`; N = nr de noduri, E = nr de muchii

## Problema 3 - Teleportare

Solutia la aceasta problema nu este neaparat corecta, doar ca exista cateva teste in care nu este nevoie sa folosim teleportarea. In aceste teste putem aplica algoritmul lui Dijkstra (sau orice alt algoritm de cel mai scurt drum) pentru a gasi raspunsul problemei. Algoritmul lui Dijkstra este cel scris la laboratorul 8.

> Complexitatea solutiei: `O(E * log(N))`; N = nr de noduri, E = nr de muchii

## Problema 4 - Magazin

La aceasta problema graful (care este chiar un arbore) se poate construi din datele de intrare astfel:

- nodul 1 va fi radacina
- parintele fiecarui nod va fi nodul (magazinul) de la care acel nod primeste livrari

Pentru a raspunde la intrebari este nevoie sa fac o parcurgere DFS a grafului (explicatiile despre cum se livreaza marfa sunt, de fapt, algoritmul de parcurgere in lungime a grafului). Este nevoie sa retin in urma acestui algoritm momentul de timp cand incep sa vizitez un nod (cand magazinul primeste marfa de la parinte) si momentul de timp cand termin de vizitat acel nod (cand magazinul trimite catre parinte mesaj ca a terminat de livrat marfa). Astfel, stiu exact magazinul care a primit marfa la orice moment t, precum si momentul cand am terminat de vizitat subarborele cu radacina in orice nod.

Pentru a raspuned la intrebari, urmez pasii de mai jos:
- se dau magazinul `m` si momentul de timp `dt` (ce magazin primeste va primi coletele plecate de la magazinul `m` dupa `dt` timp)
- caut momentul de timp cand magazinul `m` a primit marfa (cand am inceput sa il vizitez) `t1`
- calculez momentul de timp care ma intereseaza `T = t1 + dt`
- verific daca magazinul care a primit marfa la momentul de timp `T` este in subarborele cu radacina in `m`, comparand pe `T` cu timpul `t2` la care am terminat de vizitat magazinul `m`:
    - daca `t2 <= T` atunci magazinul cautat este in subarborele cu radacina in `m` si tot ce mai am de facut este sa caut ce magazin a primit marfa la momentul `T`
    - daca `t2 > T` atunci magazinul cautat NU este in subarborele cu radacina in `m` si intrebarea nu va avea raspunsul (magazinul care primeste marfa la momentul `T` nu va primi marfa de la `m`)

In plus, pentru eficienta, dupa ce termin parcurgerea DFS imi creez inca un vector in care retin in ordine cronologica ce magazin a fost vizitat in fiecare moment (pentru a gasi magazinul vizitat la momentul `T` in timp liniar).

Aplic algoritmul explicat mai sus pentru fiecare intrebare. Am integrat in solutie si alte optimizari legate de afisare (`StringBuilder` pentru a reduce timpul de afisare).

> Complexitatea solutiei: `O(N + Q)`; N = nr de noduri, Q = nr de intrebari
