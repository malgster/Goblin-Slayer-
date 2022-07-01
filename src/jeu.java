import extensions.*;

class jeu extends Program {
	//////// toutes les variables globales ////////
	int vies = 6;
	int viesBoss = 6;
	String prenom;
	long delay = 400;
	String[][] ToutesEnigmes;
	int[] posees;
	int ajoutV = 0;
	int ajoutVB = 0;
	int idxNiv = 1;
	boolean enlever = false;
	Sound OST;
	String[][] sauvegarde;
	//////////////////////////////////////////////

		void algorithm(){ // algorithme 
			PrintFile("../data/txt/pc.txt", 50);
			delay(2000);
			clearScreen();
			PlayMySound("../data/sounds/SoundMenu.mp3");
			PrintFile("../data/txt/title.txt", 50);
			println();
			println("						    Comment vous appelez vous ?");
			prenom = readString();
			clearScreen();
			println("			 Bienvenue " + prenom + " !");
			mainMenu();
		}

		void mainMenu(){ // fonction du menu principal
			PrintFile("../data/txt/menu.txt", 20);
				switch(readString()){
					case "1":
						clearScreen();
						stop(OST);
						PlayMySound("../data/sounds/StorySound.mp3");
						PrintFile("../data/txt/histoire.txt", delay);
						if (equals(readString(),"oui")){
							vies = 6 + ajoutV;
							viesBoss = 6 + ajoutVB;
							idxNiv = 1;
							jeu();
						} else {
							clearScreen();
							stop(OST);
							println("On vous force pas alors...");
							PlayMySound("../data/sounds/SoundMenu.mp3");
							mainMenu();
						}	
					case "2":
					 	clearScreen();
						LoadGame();
					case "3":
						println("Cette option étant uniquement accessible à l'enseignant, nous aurons donc besoin du mot de passe prouvant que c'est bien vous");
						String mdp = readString();
						if (equals(mdp, "JaimeLeBUTinfo")){
							config();
						}
						println("mauvais mot de passe...");
						clearScreen();
						mainMenu();
					case "4":
					    clearScreen();
						apropos();
					default:
						println("oups ! on dirait que t'as fais une erreur " + prenom + "...");
						mainMenu();
				}
			}


		void jeu(){ // Fonction qui démarre le jeu
			clearScreen();
			PrintFile("../data/txt/deb.txt", delay);
			println("Vous commencer votre quete avec " + vies + " chaussettes, prenez en bien soin");
			demanderDirection();		
		}

		void AjouterQuestions(){ // fonction d'ajout de questions 
		mainMenu();
		}
	
		void Graphic(String chemincsv) {
			CSVFile fichier = loadCSV(chemincsv);
			if (rowCount(fichier) > 0) {
				try{
					for (int lig=0; lig<rowCount(fichier); lig++) {
						for (int col=0; col<columnCount(fichier); col++) {
							background(getCell(fichier,lig,col));
							print("  ");
				    		}
				    		println();
					}
			    	} catch (Exception e) {print(e);}finally{
					println("erreure a l'affichage");
				}
			}
		}

		void config(){ // Fonction de configuration du jeu
			clearScreen();
			PrintFile("configu.txt",50);
			println("              entrez votre le nombre correspondant " + prenom);
			switch(readString()){
				case "1":
					println("Vous disposez de " + (vies+ajoutV) + " chaussettes de départs, entrez votre changement");
					String changement = readString();
					boolean good = false;
					while (!good){
						if (stringToInt(changement)<0 && abs(stringToInt(changement))>vies){
							println("Vous avez déjà vu quelqu'un avec un nombre de chaussettes négatives " + prenom + " ? réessayez...");
							changement = readString();
						} else {
							good = true;
						}
					}
					ajoutV = stringToInt(changement);
					clearScreen();
					println("Vous disposez maintenant de " + (vies+ajoutV) + " chaussettes");
					mainMenu();
				case "2":
					println("Vous disposez de " + (viesBoss+ajoutVB) + " bonnes réponses requise par niveau, entrez votre changement");
					changement = readString();
					good = false;
					while (!good){
						if (stringToInt(changement)<=0 && abs(stringToInt(changement))>vies){
							println("Mais bien sur, Un nombre négatif de bonne réponses...réessayez " + prenom);
							changement = readString();
						} else {
							good = true;
						}
					}
					ajoutVB = stringToInt(changement);
					println("Le nombre de bonne réponses requise par boss est à présent " + (viesBoss+ajoutVB));
					clearScreen();
					mainMenu();
				case "3":
					println("Le débit du défilement du texte est de " + delay + " secondes, entrez votre changement");
					changement = readString();
					good = false;
					while(!good){
						if (ItsADouble(changement)){
							println("On avait dit pas de nombre à virgule" + prenom + "... Réessayez.");
							changement = readString();
						} else {
							good = true;
						}
					}
					delay = StringToLong(changement);
					println("le débit du défilement du texte est de " + delay + " secondes");
					clearScreen();
					mainMenu();
				case "4":
					if (enlever == false) {
						println("souhaitez vous désactiver le cadeau et la malédiction des créateurs ? (tapez oui/non)");
						if (equals(readString(), "oui")){
							enlever = !enlever;
							println("Ces options ont été désactivés dans le jeu");
						} else {
							println("Vous avez choisis de les laisser activés (bonne décision)");	
						}			
					} else {
						println("voulez vous restaurer le cadeau et la malédiction des créateurs ? (tapez oui/non)");
						if (equals(readString(), "oui")){
							enlever = !enlever;
							clearScreen();
							println("Ces options ont été réactivés dans le jeu");
						} else {
							clearScreen();
							println("Vous avez choisis de ne pas les réactiver");
						}
					}
					mainMenu();
				case "5":
					println("Uniquement accessible si vous avez déjà finit le jeu et que vous aviez reçu le code secret des créateurs");
					if (equals(readString(), "AccesAConfig5")){
						println("Choisissez à quel niveau vous voulez commencer");
						changement = readString();
						good = false;
						while(!good){
							if (stringToInt(changement)>10){ //à titre indicatif, pas encore décidée pour le nombre de niveau pour l'instant
								println("le nombre maximal de niveau est 10, réessayez");
								changement = readString();
							} else {
								good = true;
							}
							idxNiv = stringToInt(changement);
							clearScreen();
							println("Vous commencerez donc votre prochaine partie au niveau " + idxNiv);
							mainMenu();
						}
					} else {
						clearScreen();
						println("mauvais mot de passe...");
						mainMenu();
					}
				case "6":
					println("Voulez vous vraiment éffacer votre partie ? Toute progression sera perdue. (oui/non)");
					if (equals(readString(), "oui")){
							String[][] save = new String[1][6];
							sauvegarde = save;
							sauvegarde[0][1] = IntToString(3);
							sauvegarde[0][2] = IntToString(3);
							sauvegarde[0][3] = IntToString(1);
							sauvegarde[0][4] = prenom;
							sauvegarde[0][5] = BooleanToString(false);

							saveCSV(sauvegarde,"save.csv");
							delay(500);
							clearScreen();
							println("votre partie a été éffacée avec succès");
							mainMenu();
					}
					clearScreen();
					mainMenu();		
				default:
					clearScreen();
					println("oups ! on dirait que t'as fais une erreur " + prenom + "...");
					mainMenu();
			}
		
		}

		void apropos(){ // Fonction qui lance le texte explicatif du jeu
			PrintFile("apropos.txt", 50);
			println();
			println("Appuyez sur une touche pour revenir au menu principal");
			if (equals(readString(), "random")){
				clearScreen();
				mainMenu();
			}
			clearScreen();
			mainMenu();
		}

		void SaveGame(){ // conserve les données que le joueur a accumulé durant la partie, qui restent meme après qu'il ferme le jeu
			sauvegarde = new String[1][6];
			sauvegarde[0][1] = IntToString(vies);
			sauvegarde[0][2] = IntToString(viesBoss);
			sauvegarde[0][3] = IntToString(idxNiv);
			sauvegarde[0][4] = prenom;
			sauvegarde[0][5] = BooleanToString(enlever);

			saveCSV(sauvegarde,"save.csv");

		}

		void LoadGame(){ // relance la partie du joueur là ou il l'avait laissé
			sauvegarde = CSVtoArray("save.csv");
			vies = stringToInt(sauvegarde[0][1]);
			viesBoss = stringToInt(sauvegarde[0][2]);
			idxNiv = stringToInt(sauvegarde[0][3]);
			prenom = sauvegarde[0][4];
			enlever = StringToBoolean(sauvegarde[0][5]);
			jeu();

		}

		long StringToLong(String chaine){ // parce que delay accepte que les long et pas les int ;)
			int res = stringToInt(chaine);
			return (long)(res);
		}


		String[][] CSVtoArray(String LeCSV){ // transforme un fichier csv en tableau de string à deux dimensions
			CSVFile csv = loadCSV(LeCSV);
			String[][] tab = new String[rowCount(csv)][columnCount(csv)];
	   		for (int lig = 0; lig < length(tab,1); lig++){
				for (int col = 0; col < length(tab,2); col++) {
					tab [lig][col] = getCell(csv, lig, col);
				}
			}
			return tab;
		}

		int LabonneLigne(String LeNiveau){ // la ligne qui indique le debut/fin des enigmes du niveau qu'on veut
			int lig = -1;
			boolean cBon = false;
			while (lig < length(ToutesEnigmes,1) && !cBon) {
				lig++;
				if (equals(ToutesEnigmes[lig][0], LeNiveau)) {
					cBon = true;
				}
			}
			return lig;
		}

		String[][] SubArray(int start, int end){ // prend la partie du tableau qui nous interesse
			String[][] CeNiveau = new String [(end-start)][length(ToutesEnigmes,2)];  // ToutesEnigmes est une variable [][]String globale
			int idxC = 0;
			int idxL = 0;
			while (idxL<(end-start) && idxC<length(ToutesEnigmes,2)){
				for (int lig = start; lig < end; lig++){
					for (int col = 0; col < length(CeNiveau,2); col++) {
						CeNiveau[idxL][idxC] = ToutesEnigmes[lig][col];
						idxC++;
					}
					idxL++;
				}
			}
			return CeNiveau;
		}

		boolean poser(Question q){ // pose la qst et permet de vérifier si la réponse donnée est la bonne, et si le joueur a effectué une bonne saisie
			println(q.ennonce);
			println(q.propositionA);
			println(q.propositionB);
			println(q.propositionC);
			boolean BonneSaisie = false;
			char res = 'z';
			while (!BonneSaisie) {
				res = readChar();
				if (res == 'a' || res == 'b' || res == 'c'){
					BonneSaisie = true;
				} else {
					println("Ca c'est pas une réponse valide, heureusement qu'on t'as pas entendu, réessaye !");
				}
			}
			boolean juste = res == q.reponse;
			if (!juste){
				println("la bonne réponse était : " + q.reponse);
			}
			return juste;
		}

		Question GetQuestion(String niveau[][]){ // retourne une question en random dans un tableau remplit de question 
			int tmp = 0;
			boolean good = false;
			while(!good){
				tmp = (int) (random()*length(niveau,1));
				if(!in(tmp)){
					append(tmp);
					good = true;
				}
			}
			Question q = new Question();
			q.ennonce = niveau[tmp][0];
			q.propositionA = niveau[tmp][1];
			q.propositionB = niveau[tmp][2];
			q.propositionC = niveau[tmp][3];
			q.reponse = charAt(niveau[tmp][4],1); // indice peut etre modifié en fonction de la position de la réponse dans le csv.
			return q;
		}

		void append(int k){ // ajoute l'indice de la question déjà posée dans un tableau de int
			int cpt = -1;
			do{
				cpt++;
			} while(cpt < length(posees) && posees[(cpt)]!=0);
			posees[cpt]=k;
		}

		boolean in(int k){ // vérifie si la question a déjà été posée en cherchant son indice dans le tableau qui stock leurs indices
			boolean out = false;
			for (int i = 0;i<length(posees) ;i++ ) {
				if(posees[i]==k){out = true;}
			}
			return out;
		}

		String IntToString(int nombre){ // utilisée dans SaveGame() (ma meilleure fonction)
			return nombre + "";	
		}

		String BooleanToString(boolean boo){ // utilisée dans SaveGame() (non celle là est mieux)
			if (boo){
				return "true";
			} else {
				return "false";
			}
		}

		boolean StringToBoolean(String boo){ //utilisée dans loadGame() (ok c'est ma meilleure fonction)
			return equals(boo, "true");
		}

		boolean ItsADouble(String nombre){ // utilisée dans config()
			boolean yes = false;
			for(int i = 0; i < length(nombre); i++){
				if (charAt(nombre, i) == '.' || charAt(nombre, i) == ','){ // car certains terminaux utilise des virgules pour les doubles
					yes = true;
					break;
				}
			}
			return yes;
		}


		void boss(){ // fonction du combat avec le boss (un combat par niveau)
			stop(OST);
			viesBoss = 3 + ajoutVB;
			Niveau n = new Niveau();
			n.texte = "../data/txt/boss" + idxNiv + ".txt";
			//n.idxDeb = n.idxDeb + idxNiv;
			//n.idxFin = n.idxFin + idxNiv;
			//n.enigmes = SubArray(LabonneLigne(n.idxDeb), LabonneLigne( n.idxFin)); 
			n.enigmes = CSVtoArray("../data/questionsBoss" + idxNiv + ".csv");
			posees = new int[15];
			PrintFile(n.texte, delay);
			PlayMySound("../data/sounds/bossSound.mp3");
			do {
				if(poser(GetQuestion(n.enigmes))){
					viesBoss--;
					println();
					println("Bravo ! votre logique inflige un dégat au boss ! \n Chaussettes restantes au boss : " + viesBoss);
				} else {
				vies--;
				println();
				println("Oh non...c'est vous qui prenez un dégat ! \n vos chaussettes restantes " + vies);
				}
			} while (viesBoss > 0 && vies > 0);
			if (vies == 0) {
				stop(OST);
				clearScreen();
				PlayMySound("../data/sounds/GameOver.mp3");
				PrintFile("gameover.txt", delay);
				println('\n' + "Appuyez sur une touche pour revenir au menu principal ");
				if (equals(readString(), "random")){
					stop(OST);
					clearScreen();
					PlayMySound("../data/sounds/SoundMenu.mp3");
					mainMenu();
				}
				stop(OST);
				clearScreen();
				PlayMySound("../data/sounds/SoundMenu.mp3");
				mainMenu();
			}
			clearScreen();
			stop(OST);
			PlayMySound("../data/sounds/VictorySound.mp3");
			println("Bravo à vous chevalier ! Mais votre quete n'est point encore terminée...");
			idxNiv++;
			if (enlever==false){
				Cadeau();
				malediction();
			}
			println("L'un des autels des dévellopeurs se trouve près de vous, voulez vous sauvegarder votre progression ? (oui/non)");
			if (equals(readString(), "oui")) {
				println("veuillez patienter...");
				SaveGame();
				delay(1000);
				println();
				println("Les saints dévellopeurs ont prit note de votre progression, vous pouvez y aller chevalier " + prenom);
			} else {
			println();
			println("Vous décidez de ne pas y aller.");
			}
			demanderDirection();
		}

		void entrainement(){ // fonction d'entrainement (un entrainement par niveau)
			clearScreen();
			PrintFile("../data/txt/colisee.txt", 50);
			Niveau n = new Niveau();
			n.texte = "map" + idxNiv + ".txt";
			//n.idxDeb = n.idxDeb + idxNiv + "(E)";
			//n.idxFin = n.idxFin + idxNiv + "(E)";
			//n.enigmes = SubArray(LabonneLigne(n.idxDeb), LabonneLigne(n.idxFin));
			n.enigmes = CSVtoArray("questionsBoss" + idxNiv + ".csv");
			posees = new int[15];
			PrintFile(n.texte, delay);
			int reps = 0;
			do {
				if(poser(GetQuestion(n.enigmes))){
					println();
					println("Et c'est une bonne réponse ! bravo !");
					reps++;
				} else {
				println();
				println("mauvaise réponse...faudra faire attention face au boss...");
				reps++;
				}
			} while (reps < 5);
			clearScreen();
			println("vous etes a présent pret a aller affronter le boss");
			boss();
	    }

	void PrintFile(String FichierTexte, long time) { // fonction qui permet de lire des fichier texte ligne par ligne
		File fichier = newFile(FichierTexte);
        while(ready(fichier)){           //s'arrête dès qu'on lit une ligne null (cad derniere ligne)
	    println(readLine(fichier));
		delay(time);
		}
	}

	void PlayMySound(String mySound){ // joue de la musique en background
	OST = newSound(mySound);
	   play(OST,true);
	}

	boolean Cadeau(){ //ajout d'une vie en aléatoire
		int proba = (int) (random()*3);
		if (proba == 2){
			delay(1000);
			println(prenom + " ! Ici les saints dévéllopeurs, nous avons réussi à vous générer une chaussette en plus, prenez en soin ! ");
			vies = vies++;
			delay(1000);	
		}
		return true;
	}

	boolean malediction(){ //reduction d'une vie en aléatoire (moins probable ;))
		int proba = (int) (random()*8);
		if (proba == 5){
			println("Dans votre chemin, vous trouvez une foret, et vous décidez dans votre grande fatigue d'y passer la nuit...");
			vies = vies--;
			delay(1000);
			println("le lendemain vous reveillez avec la mauvaise surprise de voir une de vos chaussettes volées ! Pas de chance..." + '\n' + "Nombres de chaussettes : " + vies );
		}
		return true;
	}
	

	void demanderDirection(){ // fonction de déplacement
		println("Voulez vous aller vers le colisee d'entrainement, ou es ce que vous vous sentez d'attaque pour combattre le prochain boss ? (entrez 1 ou 2)");
		switch(readString()){
			case "1":
				clearScreen();
				entrainement();
			case "2":
				clearScreen();
				boss();
			default:
				println("oh oh...vous allez vers un chemin douteux chevalier...revenez sur vos pas il vaut mieux.");
				demanderDirection();
		}
	}

}

