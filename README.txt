__________________________________________________________

Credenziali per l'accesso Admin:
__________________________________________________________

Username: AdminNP
Password: spacchi



__________________________________________________________

METODO D'AVVIO #1
___________________________________________________________

Per eseguire il flie .jar (Spacchi.jar)>
Dal prompt dei comandi scrivere:

java --module-path "......" --add-modules javafx.controls,javafx.fxml -jar "......." 
Nel primo spazio vuoto ".." inserire il path delle librerie javafx, mentre nel secondo inserire il path del .jar eseguibile (o trascinando semplicemente).

ESEMPIO: java --module-path "C:\Users\BS00000000\Desktop\javafx-sdk-20.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar "C:\Users\BS00000000\Desktop\Spacchi.jar"

Dal prompt dei comandi, premere invio e l'applicazione dovrebbe partire.

__________________________________________________________

METODO D'AVVIO #2
___________________________________________________________

Creare (o modificare quello fornito) un file di testo .txt
Rinominarlo (per comodità lo chiameremo "run") e cambiargli il formato da .txt >> .bat
Bisogna poi modificarlo, tramite click destro > mostra altre opzioni (wind 11) > modifica , usando il notepad (o simili)
e scrivere la riga:

java --module-path "......" --add-modules javafx.controls,javafx.fxml -jar "......." 
Nel primo spazio vuoto ".." inserire il path delle librerie javafx, mentre nel secondo inserire il nome.jar eseguibile dell'applicazione.

ESEMPIO: java --module-path "C:\Users\BS00000000\Desktop\javafx-sdk-20.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar Spacchi.jar

Fare poi doppio click su "run.bat" e dovrebbe partire.
