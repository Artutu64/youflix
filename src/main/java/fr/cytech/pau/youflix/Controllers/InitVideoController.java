package fr.cytech.pau.youflix.Controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RandomUtil;

@Controller
public class InitVideoController {

    @Autowired
    CategorieRepository genreRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    VideoRepository videoRepository;
    
    public void ajouter( String titreVideo, String dateSortieVideo, String descriptionVideo,  String acteurs, String genres, String lienVideo){
        Set<Acteur> setActeursFilm = new HashSet<>();
        Set<Categorie> setGenresFilm = new HashSet<>();
        SimpleDateFormat formatDate;
        Date date = new Date(LocalDate.now().toEpochDay());;
        Date dateSortieVideoSQL;
        Video video;
        setActeursFilm = new HashSet<>();
        if (acteurs != null) {
            String[] listeActeurs = acteurs.split(",");
            for (int i=0; i<listeActeurs.length; i++) {
                listeActeurs[i] = listeActeurs[i].trim();
                String[] nomPrenom = listeActeurs[i].split("_");
                String nomActeur = nomPrenom[0];
                String prenomActeur = nomPrenom[1];
                List<Acteur> acteursExistants = acteurRepository.findActorByNom(nomActeur);
                boolean acteurExiste = false;
                for (Acteur acteur : acteursExistants) {
                    if (nomActeur.equals(acteur.getNom()) && prenomActeur.equals(acteur.getPrenom())) {
                        acteurExiste = true;
                        setActeursFilm.add(acteur);
                    }
                }
                if (!acteurExiste) {
                    Acteur acteur = new Acteur();
                    acteur.setIdActeur(RandomUtil.getRandomId());
                    acteur.setNom(nomActeur);
                    acteur.setPrenom(prenomActeur);
                    acteurRepository.save(acteur);
                    setActeursFilm.add(acteur);
                }
            }
        }
        setGenresFilm = new HashSet<>();
        if (genres != null) {
            String[] listeGenres = genres.split(",");
            for (int i=0; i<listeGenres.length; i++) {
                listeGenres[i] = listeGenres[i].trim();
                List<Categorie> genresExistants = genreRepository.findCategorieByNom(listeGenres[i]);
                boolean genreExiste = false;
                for (Categorie genre : genresExistants) {
                    if (listeGenres[i].equals(genre.getNom())) {
                        genreExiste = true;
                        setGenresFilm.add(genre);
                    }
                }
                if (!genreExiste) {
                    Categorie genre = new Categorie();
                    genre.setNom(listeGenres[i]);
                    genreRepository.save(genre);
                    setGenresFilm.add(genre);
                }
            }
        }
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatDate.parse(dateSortieVideo);
        } catch(Exception e){ }
        dateSortieVideoSQL = new Date(date.getTime());
        video = new Video();
        video.setCodeVideo(lienVideo);
        video.setDescription(descriptionVideo);
        video.setTitre(titreVideo);
        video.setCategories(setGenresFilm);
        video.setDateSortie(dateSortieVideoSQL); 
        video.setJoueDans(setActeursFilm);
        videoRepository.save(video);  
    }
    
    public void ajout0(){
        ajouter("Snatch, tu braques ou tu raques"
        ,"2000-09-01"
        ,"Franky vient de voler un énorme diamant qu’il doit livrer à Avi, un mafieux new‐yorkais. En chemin, il fait escale à Londres où il se laisse convaincre par Boris de parier sur un combat de boxe clandestin. Il ignore, bien sûr, qu’il s’agit d’un coup monté avec Vinny et Sol, afin de le délester de son magnifique caillou. Turkish et Tommy, eux, ont un problème avec leur boxeur, un gitan complètement fêlé qui refuse de se coucher au quatrième round comme prévu. C’est au tour d’Avi de débarquer, bien décidé à récupérer son bien, avec l’aide de Tony, une légende de la gâchette."
        ,"Statham_Jason"
        ,"Crime,Comédie"
        ,"Ib6EsAQNlV0");
    }

    public void ajout1(){
        ajouter("En eaux troubles"
        ,"2018-08-09"
        ,"Missionné par un programme international d\'observation de la vie sous-marine, un submersible a été attaqué par une créature gigantesque qu\'on croyait disparue. Sérieusement endommagé, il gît désormais dans une fosse, au plus profond de l\'océan Pacifique, où son équipage est pris au piège. Il n\'y a plus de temps à perdre : Jonas Taylor, sauveteur-plongeur expert des fonds marins, est engagé par un océanographe chinois particulièrement visionnaire, contre l\'avis de sa fille Suyin."
        ,"Statham_Jason,Sophia_Shuya"
        ,"Action,Science-Fiction,Horreur"
        ,"X4G31LDLtlM");
    }

    public void ajout2(){
        ajouter("Le Transporteur"
        ,"2002-10-02"
        ,"Pour les livraisons à haut risque, Franck est toujours là. Comme les autres, il obéit aux trois règles d\'or : ne poser aucune question, ne pas ouvrir les colis et ne pas enfreindre les deux premières au risque d\'y trouver la mort. Mais cette fois-ci, Franck a ouvert le sac posé dans son coffre et a découvert une jeune femme se nommant Lai. Face à ce cas de conscience et à une sombre affaire de trafic humain, il ne va plus pouvoir fermer les yeux et décide d\'aider ce \"colis\" un peu spécial."
        ,"Statham_Jason"
        ,"Action,Crime,Thriller"
        ,"7FnbLyv2oio");
    }

    public void ajout6(){
        ajouter("MAD"
        ,"2023-10-06"
        ,""
        ,"Shobhan_Sangeeth"
        ,"Comédie,Romance,Drame"
        ,"tpKQtYWiRDY");
    }

    public void ajout8(){
        ajouter("Ghosted"
        ,"2023-04-18"
        ,"Cole, un agriculteur sans histoire, tombe éperdument amoureux de la mystérieuse Sadie, mais découvre avec stupeur qu\'elle est agent secret. Avant même un second rencard, Cole et Sadie vont être embarqués dans une aventure au-delà des frontières lors de laquelle ils vont devoir sauver le monde."
        ,"Broadway_Lizze"
        ,"Action,Comédie,Romance"
        ,"9nP9aXML9xE");
    }

    public void ajout9(){
        ajouter("The Dark Knight : Le Chevalier noir"
        ,"2008-07-16"
        ,"Batman aborde une phase décisive de sa guerre contre le crime à Gotham City. Avec l\'aide du lieutenant de police Jim Gordon et du nouveau procureur Harvey Dent, il entreprend de démanteler les dernières organisations criminelles qui infestent les rues de la ville. L\'association s\'avère efficace, mais le trio se heurte bientôt à un nouveau génie du crime qui répand la terreur et le chaos dans Gotham : le Joker. On ne sait pas d\'où il vient ni qui il est. Ce criminel possède une intelligence redoutable doublé d\'un humour sordide et n\'hésite pas à s\'attaquer à la pègre locale dans le seul but de semer le chaos."
        ,"Oldman_Gary,Caine_Michael,Nolan_Christopher,Bale_Christian"
        ,"Drame,Action,Crime,Thriller"
        ,"UMgb3hQCb08");
    }

    public void ajout10(){
        ajouter("The Dark Knight Rises"
        ,"2012-07-17"
        ,"Afin que l\'image de l\'ex-procureur Harvey Dent reste un modèle du genre pour les citoyens de Gotham City, Batman a endossé les crimes de ce dernier et a été chassé de la ville par les autorités.  Huit ans plus tard, alors que Gotham City est de nouveau sûre, le commissaire Gordon tombe sur un complot qui vise à détruire la ville de l\'intérieur.  Il fait appel à Batman. Ce dernier devra faire face à la mystérieuse Selina Kyle et à Bane, un adversaire mortel, qui veut mettre en pièce le symbole Batman."
        ,"Oldman_Gary,Caine_Michael,Bale_Christian"
        ,"Action,Crime,Drame,Thriller"
        ,"OiqPQ7L_C00");
    }

    public void ajout11(){
        ajouter("Les heures sombres"
        ,"2017-11-22"
        ,"En mai 1940, Winston Churchill devient Premier ministre du Royaume-Uni, en pleine Seconde Guerre mondiale. Sa détermination à combattre les Allemands sera décisive pour la suite des événements. Sa nomination au poste de premier ministre britannique fut un grand hasard, bien qu\'il ait reçu de vives critiques sur sa politique anti-Hitler; ses discours, qui firent la une des journaux de l\'époque, résonnent jusqu\'à aujourd\'hui."
        ,"Oldman_Gary"
        ,"Drame,Histoire"
        ,"TFDumAPV0LY");
    }

    public void ajout12(){
        ajouter("La Nuit au musée"
        ,"2006-12-20"
        ,"Le Muséum d\'Histoire Naturelle renferme dans ses murs un secret mystérieux et stupéfiant que Larry, nouveau gardien de sécurité, ne va pas tarder à découvrir avec affolement : la nuit, toutes les expositions prennent vie ! Sous ses yeux, les soldats romains et les cow-boys miniatures se lancent dans une guerre acharnée, Attila et ses Huns commencent à piller les expositions avoisinantes, la momie tente de sortir de son sarcophage, le squelette du redoutable – mais néanmoins attachant – tyrannosaure rôde dans les couloirs... Le chaos est total ! Larry pourra-t-il remettre de l\'ordre dans ses collections et sauver le musée ?"
        ,"Gugino_Carla"
        ,"Action,Aventure,Comédie,Familial,Fantastique"
        ,"fY8F6Ymg2B8");
    }

    public void ajout14(){
        ajouter("San Andreas"
        ,"2015-05-27"
        ,"Lorsque la tristement célèbre Faille de San Andreas finit par s\'ouvrir, et par provoquer un séisme de magnitude 9 en Californie, un pilote d\'hélicoptère de secours en montagne et la femme dont il s\'est séparé quittent Los Angeles pour San Francisco dans l\'espoir de sauver leur fille unique. Alors qu\'ils s\'engagent dans ce dangereux périple vers le nord de l\'État, pensant que le pire est bientôt derrière eux, ils ne tardent pas à comprendre que la réalité est bien plus effroyable encore…"
        ,"Gugino_Carla,Daddario_Alexandra"
        ,"Action,Drame,Thriller"
        ,"HGpwaIf3ZKk");
    }

    public void ajout15(){
        ajouter("Fast & Furious : Hobbs & Shaw"
        ,"2019-08-01"
        ,"Depuis que Hobbs, fidèle agent de sécurité au service diplomatique des États-Unis, combatif mais droit, et Shaw, un homme sans foi ni loi, ancien membre de l’élite militaire britannique, se sont affrontés en 2015 dans Fast & Furious 7, les deux hommes font tout ce qu’ils peuvent pour se nuire l’un à l’autre. Mais lorsque Brixton, un anarchiste génétiquement modifié, met la main sur une arme de destruction massive après avoir battu le meilleur agent du MI6 qui se trouve être la sœur de Shaw, les deux ennemis de longue date vont devoir alors faire équipe pour faire tomber le seul adversaire capable de les anéantir."
        ,"Kirby_Vanessa"
        ,"Action,Aventure,Comédie"
        ,"QUu7QlWPa-g");
    }

    public void ajout16(){
        ajouter("Avant toi"
        ,"2016-06-01"
        ,"Une charmante petite ville de l\'Angleterre rurale. Si elle est originale et artiste dans l\'âme, Louisa «Lou» Clark, 26 ans, n\'a aucune ambition particulière. Elle se contente d\'enchaîner les boulots pour permettre à ses proches de joindre les deux bouts. Jeune et riche banquier, Will Traynor était un garçon plein d\'audace et d\'optimisme jusqu\'à ce qu\'il se retrouve paralysé, suite à un accident survenu deux ans plus tôt. Devenu cynique, il a renoncé à tout et n\'est plus que l\'ombre de lui-même. Autant dire que ces deux-là auraient pu ne jamais se rencontrer. Mais lorsque Lou accepte de travailler comme aide-soignante auprès de Will, elle est bien décidée à lui redonner goût à la vie. Et peu à peu, les deux jeunes gens s\'éprennent passionnément l\'un de l\'autre. La force de leur amour pourra-t-elle survivre à leur destin qui semble inexorable ?"
        ,"Kirby_Vanessa"
        ,"Drame,Romance"
        ,"3zDnx3n-O8M");
    }

    public void ajout17(){
        ajouter("Pieces of a Woman"
        ,"2020-12-30"
        ,"Vivant à Boston, Martha et Sean Carson s’apprêtent à devenir parents. Mais la vie du couple est bouleversée lorsque la jeune femme accouche chez elle et perd son bébé, malgré l’assistance d’une sage-femme, bientôt poursuivie pour acte de négligence. Martha doit alors apprendre à faire son deuil, tout en subissant une mère intrusive et un mari de plus en plus irritable. Mais il lui faut aussi assister au procès de la sage-femme, dont la réputation est désormais détruite. Pieces of a Woman est une chronique intimiste et déchirante de la vie d’un couple, et le portrait bouleversant d’une femme qui doit apprendre à faire son travail de deuil."
        ,"Kirby_Vanessa"
        ,"Drame"
        ,"1zLKbMAZNGI");
    }

    public void ajout18(){
        ajouter("Interstellar"
        ,"2014-11-05"
        ,"Dans un futur proche, face à une Terre exsangue, un groupe d’explorateurs utilise un vaisseau interstellaire pour franchir un trou de ver permettant de parcourir des distances jusque‐là infranchissables. Leur but : trouver un nouveau foyer pour l’humanité."
        ,"Hathaway_Anne,Caine_Michael,McConaughey_Matthew,Nolan_Christopher"
        ,"Aventure,Drame,Science-Fiction"
        ,"VaOijhK3CRU");
    }

    public void ajout19(){
        ajouter("Le Diable s’habille en Prada"
        ,"2006-06-29"
        ,"Fraîchement diplômée, Andrea débarque à New York et décroche le job de rêve. Mais en tant qu’assistante de la tyrannique rédactrice en chef d’un prestigieux magazine de mode, elle va vite découvrir ce que le mot « enfer » veut dire…"
        ,"Hathaway_Anne"
        ,"Comédie,Drame,Romance"
        ,"SkXERnN-vzw");
    }

    public void ajout20(){
        ajouter("Alice de l\'autre côté du miroir"
        ,"2016-05-25"
        ,"Les nouvelles aventures d’Alice et du Chapelier Fou. Alice replonge au pays des merveilles pour aider ses amis à combattre le Maître du Temps."
        ,"Hathaway_Anne"
        ,"Aventure,Familial,Fantastique"
        ,"YLt8tpRb05o");
    }

    public void ajout21(){
        ajouter("En eaux très troubles"
        ,"2023-08-02"
        ,"Une équipe de chercheurs part explorer les profondeurs de l\'océan. Leur périple tourne à la catastrophe lorsqu\'une opération d\'extraction minière illégale met en péril leur mission – et leur vie. Confrontés à d\'immenses mégalodons et à des bandits sans pitié, nos héros doivent échapper aux terribles prédateurs en gardant toujours un temps d\'avance sur eux dans une terrifiante course contre la montre."
        ,"Sophia_Shuya"
        ,"Action,Science-Fiction,Horreur"
        ,"yNmxVeCL39o");
    }

    public void ajout23(){
        ajouter("Hugas"
        ,"2022-01-14"
        ,""
        ,"Raval_AJ"
        ,"Thriller,Romance,Crime,Drame"
        ,"fOvKT4dWJfM");
    }

    public void ajout26(){
        ajouter("Rush Hour"
        ,"1998-09-18"
        ,"L\'inspecteur Lee est un des fleurons de la police royale de Hong Kong. Sa placidité ne laisse rien soupçonner de son génie des arts martiaux. Âgée de onze ans, la fille du consul de Chine aux États-Unis fut son élève préférée, il en était le garde du corps, il en est resté le meilleur ami. Lorsqu\'elle est enlevée par les hommes d\'un maître du crime, qui a jadis abattu son coéquipier, Lee saute dans l\'avion, bien décidé à retrouver la fillette et à châtier ses ravisseurs."
        ,"Chan_Jackie"
        ,"Action,Comédie,Crime"
        ,"p_l_CUWMeLY");
    }

    public void ajout27(){
        ajouter("Rush Hour 2"
        ,"2001-08-03"
        ,"L\'agent James Carter rejoint son vieil ami l\'inspecteur Lee pour passer, à Hong Kong, ce qui devrait être de reposantes vacances. Mais lorsqu\'une bombe explose à l\'ambassade américaine, tuant deux agents des douanes enquêtant sur un trafic de faux billets. La police suspectant Ricky Tan, chef de la Triade, confie l\'affaire à Lee. À regret, Carter voit ses projets de repos s\'écrouler, d\'autant plus que pour son compère, l\'affaire est personnelle : Ricky Tan était autrefois le partenaire de son père dans la police et a joué un rôle direct dans sa mort... Cette fois, c\'est Lee qui guide son collègue dans un pays inconnu, et quand on connaît le bon caractère de Carter et sa discrétion, nul doute que tout sera simple... De Hong Kong à Los Angeles et Las Vegas, l\'enquête risque d\'être explosive."
        ,"Chan_Jackie"
        ,"Action,Comédie,Crime"
        ,"SCTzYY95Aw4");
    }

    public void ajout28(){
        ajouter("Rush Hour 3"
        ,"2007-08-08"
        ,"Dans quelques heures, l\'ambassadeur chinois Han compte révéler l\'identité du maître du plus puissant syndicat criminel du monde, les Triades chinoises. Lee est chargé de le protéger. Lorsque Carter, relégué depuis quelque temps à la circulation, apprend que l\'ambassadeur vient de se faire tirer dessus, il se précipite sur les lieux pour intervenir, mais il ne parvient qu\'à gêner Lee et à permettre à l\'assassin de s\'enfuir."
        ,"Chan_Jackie"
        ,"Action,Comédie,Crime"
        ,"gliQgQswQP0");
    }

    public void ajout29(){
        ajouter("KAITHI"
        ,"2019-10-25"
        ,"Un ex-détenu en liberté conditionnelle est obligé d\'aider la police afin qu\'il puisse voir sa fille pour la première fois de sa vie."
        ,"Kanagaraj_Lokesh,Siva_Monekha"
        ,"Action,Thriller"
        ,"g79CvhHaj5I");
    }

    public void ajout30(){
        ajouter("Vikram"
        ,"2022-06-02"
        ,"Amar est chargé d\'enquêter sur une affaire de meurtres en série. En enquêtant sur l\'affaire, il se rend compte que les choses ne sont pas ce qu\'elles semblent être et que suivre cette voie ne mènera qu\'à une guerre entre toutes les personnes impliquées."
        ,"Kanagaraj_Lokesh"
        ,"Action,Aventure,Crime,Thriller"
        ,"OKBMCL-frPU");
    }

    public void ajout31(){
        ajouter("MASTER"
        ,"2021-01-13"
        ,"JD est un professeur faisant face à des problèmes de dépendance d’alcool. Il est obligé à quitter l\'université pour aller enseigner dans un centre de détention juvénile pendant quelques mois. Alors que JD se la coule douce, il réalise que les jeunes de ce centre sont en fait exploités par Bhavani, un gangster cruel."
        ,"Kanagaraj_Lokesh"
        ,"Action,Thriller,Crime,Aventure"
        ,"IGNyFDEwpNE");
    }

    public void ajout32(){
        ajouter("Un homme d\'exception"
        ,"2001-12-14"
        ,"En 1947, étudiant les mathématiques à l’université de Princeton, John Forbes Nash Jr., un brillant élève, élabore sa théorie économique des jeux. Pour lui, les fluctuations des marchés financiers peuvent être calculées très précisément. Au début des années cinquante, ses travaux et son enseignement au Massachusetts Institute of Technology ne passent pas inaperçus et un représentant du Département de la Défense, William Parcher, se présente à lui pour lui proposer d’aider secrètement les États‐Unis. La mission de John consiste à décrypter dans la presse les messages secrets d’espions russes, censés préparer un attentat nucléaire sur le territoire américain. Celui‐ci y consacre rapidement tout son temps, et ce au détriment de sa vie de couple avec Alicia. Ce job n’est toutefois pas sans risques : des agents ennemis surveillent ses moindres faits et gestes. Mais personne ne le croit."
        ,"Connelly_Jennifer,Crowe_Russell"
        ,"Drame,Romance"
        ,"tZJufJqEccA");
    }

    public void ajout34(){
        ajouter("Noé"
        ,"2014-03-07"
        ,"Noé, un père de famille, reçoit un message de Dieu au cours d’un rêve : la Terre s’apprête à subir un déluge apocalyptique, car l’homme a corrompu le monde à force de violence et d’avidité. Il part alors avec sa femme et ses enfants sur le mont Ararat et entreprend la construction d’une arche monumentale pour mettre à l’abri toutes les espèces existantes de l’humanité, sauver les innocents et préserver la vie sur Terre. Il accomplit ainsi son destin hors du commun. Mais il se heurte à un seigneur de la guerre qui cherche à régner sur ce monde dévasté, et qui lance une armée entière contre lui…"
        ,"Connelly_Jennifer"
        ,"Drame,Aventure"
        ,"tX6AIXyNR2I");
    }

    public void ajout35(){
        ajouter("Doctor Sleep"
        ,"2019-10-30"
        ,"Encore profondément marqué par le traumatisme qu\'il a vécu, enfant, à l\'Overlook Hotel, Dan Torrance a dû se battre pour tenter de trouver un semblant de sérénité. Mais quand il rencontre Abra, courageuse adolescente aux dons extrasensoriels, ses vieux démons resurgissent. Car la jeune fille, consciente que Dan a les mêmes pouvoirs qu\'elle, a besoin de son aide : elle cherche à lutter contre la redoutable Rose Claque et sa tribu du Nœud Vrai qui se nourrissent des dons d\'innocents comme elle pour conquérir l\'immortalité. Formant une alliance inattendue, Dan et Abra s\'engagent dans un combat sans merci contre Rose. Face à l\'innocence de la jeune fille et à sa manière d\'accepter son don, Dan n\'a d\'autre choix que de mobiliser ses propres pouvoirs, même s\'il doit affronter ses peurs et réveiller les fantômes du passé…"
        ,"Parker_Katie"
        ,"Horreur,Thriller,Fantastique"
        ,"UbjeO3xoGY8");
    }

    public void ajout36(){
        ajouter("Absentia"
        ,"2011-03-03"
        ,"Le mari de Tricia manque à l\'appel depuis sept ans. Au moment où sa jeune sœur Callie vient s\'installer chez elle, Tricia doit se résoudre à faire officialiser la présomption de décès de son mari. Alors que Tricia ressasse ses souvenirs, Callie se sent attirée vers un mystérieux tunnel qui s\'ouvre près de la maison. Elle apprend ainsi l\'existence d\'autres disparitions dans le quartier. Il semble même que l\'entité qui œuvre dans le tunnel ait également des visées sur Callie et Tricia."
        ,"Parker_Katie"
        ,"Mystère,Horreur,Thriller"
        ,"2bkFyCagx8s");
    }

    public void ajout37(){
        ajouter("The Mirror"
        ,"2013-09-08"
        ,"Au bout de 11 années, Tim est autorisé à quitter l\'hôpital psychiatrique. Le jeune homme de 21 ans ne rêve que de faire un trait sur son passé et de reprendre une existence normale. Sa soeur aînée Kaylie le replonge pourtant dans ses souvenirs, le ramenant à la demeure familiale où il s\'est produit une tragédie entre maman et papa. Kaylie est convaincue qu\'un vieux miroir est responsable de ce cauchemar et elle tente de le prouver en posant des caméras dans la maison. Rapidement, le frère et la soeur commencent à halluciner, étant incapable de séparer le vrai du faux."
        ,"Parker_Katie"
        ,"Horreur"
        ,"09LIqK4eJew");
    }

    public void ajout38(){
        ajouter("Captain America : First Avenger"
        ,"2011-07-22"
        ,"1941, la Seconde Guerre Mondiale fait rage. Après avoir tenté vainement de s\'engager dans l\'armée pour se battre aux côtés des Alliés, Steve Rogers, frêle et timide, se porte volontaire pour participer à un programme expérimental qui va le transformer en un Super Soldat connu sous le nom de Captain America. Sous le commandement du colonel Chester Phillips, il s\'apprête à affronter HYDRA, l\'organisation scientifique secrète des nazis dirigée par le redoutable Crâne Rouge, aux côtés de Bucky Barnes et Peggy Carter..."
        ,"Atwell_Hayley"
        ,"Action,Aventure,Science-Fiction"
        ,"JerVrbLldXw");
    }

    public void ajout39(){
        ajouter("Captain America : Le Soldat de l\'hiver"
        ,"2014-03-20"
        ,"Après les événements cataclysmiques de New York de The Avengers, Steve Rogers aka Captain America vit tranquillement à Washington, D.C. et essaye de s\'adapter au monde moderne. Mais quand un collègue du S.H.I.E.L.D. est attaqué, Steve se retrouve impliqué dans un réseau d\'intrigues qui met le monde en danger. S\'associant à Black Widow, Captain America lutte pour dénoncer une conspiration grandissante, tout en repoussant des tueurs professionnels envoyés pour le faire taire. Quand l\'étendue du plan maléfique est révélée, Captain America et Black Widow sollicite l\'aide d\'un nouvel allié, le Faucon. Cependant, ils se retrouvent bientôt face à un inattendu et redoutable ennemi - le Soldat de l\'Hiver."
        ,"Atwell_Hayley,Johansson_Scarlett"
        ,"Action,Aventure,Science-Fiction"
        ,"6mQWmUwxZjI");
    }

    public void ajout40(){
        ajouter("Braveheart"
        ,"1995-05-24"
        ,"À la fin du XIIIème siècle, le roi Edward 1er d\'Angleterre s\'empare du trône d\'Ecosse, réduisant son peuple à la misère. William Wallace donne le signe de la révolte et, à la tête d\'une armée de gueux, parvient à reconquérir plusieurs places fortes. Mais les nobles écossais sont lâchés et par deux fois trahissent le héros de leur peuple..."
        ,"Gibson_Mel"
        ,"Action,Drame,Histoire,Guerre"
        ,"ePBRBuYEuHk");
    }

    public void ajout41(){
        ajouter("Signes"
        ,"2002-08-02"
        ,"À Bucks County, en Pennsylvanie. Après la perte de sa femme, Graham Hess a rendu sa charge de pasteur. Tout en s\'occupant de sa ferme, il tente d\'élever de son mieux ses deux enfants, Morgan et Bo. Son jeune frère Merrill, une ancienne gloire du base-ball, est revenu vivre avec lui pour l\'aider. Un matin, la petite famille découvre l\'apparition dans ses champs de gigantesques signes et cercles étranges. Des extra-terrestres seraient-ils à l\'origine de tels phénomènes surnaturels ? Graham et Merrill vont s\'efforcer de percer le mystère qui entoure ces dessins."
        ,"Gibson_Mel"
        ,"Drame,Thriller,Science-Fiction,Mystère"
        ,"1V0XQNdcs78");
    }

    public void ajout42(){
        ajouter("L\'Arme fatale"
        ,"1987-03-06"
        ,"Roger Murtaugh, policier noir proche de la retraite et père de famille, se voit adjoindre comme coéquipier Martin Riggs dit \"L\'arme fatale\", policier exemplaire qui joue au trompe-la-mort depuis le décès de sa femme, et qui l\'entraîne dans le démantèlement d\'un réseau de trafiquants. En retour, ces derniers kidnappent la fille de Roger..."
        ,"Gibson_Mel"
        ,"Aventure,Action,Comédie,Thriller,Crime"
        ,"OeBVtCT6HW8");
    }

    public void ajout43(){
        ajouter("Les Croods"
        ,"2013-03-15"
        ,"Lorsque la caverne où ils vivent depuis toujours est détruite et leur univers familier réduit en miettes, les Croods se retrouvent obligés d’entreprendre leur premier grand voyage en famille. Entre conflits générationnels et bouleversements sismiques, ils vont découvrir un nouveau monde fascinant, rempli de créatures fantastiques, et un futur au‐delà de tout ce qu’ils avaient imaginé."
        ,"Cage_Nicolas"
        ,"Animation,Aventure,Familial,Fantastique,Comédie,Action"
        ,"9RfgCxNtrWA");
    }

    public void ajout44(){
        ajouter("Benjamin Gates et le Trésor des Templiers"
        ,"2004-11-19"
        ,"Perdu depuis plus de 200 ans, le trésor des Chevaliers du Temple fait partie de ces mythiques légendes. Ben Gates, archéologue et aventurier, se lance à la recherche de ce trésor qui a hanté les pensées de sa famille et de ses descendants depuis des générations. Mais il n\'est pas le seul intéressé. Gates et son meilleur ami Riley Poole, expert en informatique, partent en exploration sur le continent Arctique et découvrent que le premier indice menant au trésor est caché dans le document le mieux gardé au monde, la Déclaration d\'Indépendance. Dans une course contre le temps, ils doivent voler le document si précieux, décoder la carte cachée, semer le FBI, et éviter d\'être tués par Ian Howe, un riche aventurier anglais. Et ce n\'est que la première étape de cette chasse au trésor."
        ,"Cage_Nicolas"
        ,"Aventure,Action,Thriller,Mystère"
        ,"TJI0PCRANnI");
    }

    public void ajout45(){
        ajouter("Ghost Rider"
        ,"2007-01-15"
        ,"Johnny Blaze n’était qu’un cascadeur adolescent lorsqu’il vendit son âme au diable. Aujourd’hui, célèbre casse-cou le jour, il incarne le légendaire Ghost Rider la nuit. Chasseur de prime du diable, il doit traquer les âmes diaboliques sur Terre et les retourner en enfer. Mais une rencontre inespérée avec l’amour de sa vie le pousse à croire qu’il peut retrouver le bonheur, s’il vainc le diable et reprend son âme. Pour réussir, il devra terrasser Blackheart, le fils rebelle du diable, qui complote de faire régner le mal sur Terre en renversant son père, à moins que Ghost Rider ne l’arrête."
        ,"Cage_Nicolas"
        ,"Thriller,Action,Fantastique"
        ,"CY9nq-i92zM");
    }

    public void ajout46(){
        ajouter("Blade Runner 2049"
        ,"2017-10-04"
        ,"En 2049, la société est fragilisée par les nombreuses tensions entre les humains et leurs esclaves créés par bioingénierie. L’officier K est un Blade Runner : il fait partie d’une force d’intervention d’élite chargée de trouver et d’éliminer ceux qui n’obéissent pas aux ordres des humains. Lorsqu’il découvre un secret enfoui depuis longtemps et capable de changer le monde, les plus hautes instances décident que c’est à son tour d’être traqué et éliminé. Son seul espoir est de retrouver Rick Deckard, un ancien Blade Runner qui a disparu depuis des décennies…"
        ,"De Armas_Ana,Gosling_Ryan"
        ,"Science-Fiction,Drame"
        ,"O4C5cwSbXZ8");
    }

    public void ajout47(){
        ajouter("À couteaux tirés"
        ,"2019-11-27"
        ,"Célèbre auteur de polars, Harlan Thrombey est retrouvé mort dans sa somptueuse propriété, le soir de ses 85 ans. L’esprit affûté et la mine débonnaire, le détective Benoit Blanc est alors engagé par un commanditaire anonyme afin d’élucider l’affaire. Mais entre la famille d’Harlan qui s\'entre-déchire et son personnel qui lui reste dévoué, Blanc plonge dans les méandres d’une enquête mouvementée, mêlant mensonges et fausses pistes, où les rebondissements s\'enchaînent à un rythme effréné jusqu\'à la toute dernière minute…"
        ,"De Armas_Ana"
        ,"Comédie,Crime,Mystère"
        ,"OOTOwlSIIdU");
    }

    public void ajout48(){
        ajouter("War Dogs"
        ,"2016-08-18"
        ,"Deux copains âgés d’une vingtaine d’années, vivant à Miami pendant la guerre en Irak, profitent d’un dispositif du gouvernement fédéral totalement méconnu, permettant à de petites entreprises de répondre à des appels d’offres de l’armée américaine. Si leurs débuts sont modestes, ils ne tardent pas à empocher de grosses sommes d’argent et à mener la grande vie. Mais les deux amis sont totalement dépassés par les événements lorsqu’ils décrochent un contrat de 300 millions de dollars destiné à armer les soldats afghans. Car, pour honorer leurs obligations, ils doivent entrer en contact avec des individus très peu recommandables… dont certains font partie du gouvernement américain…"
        ,"De Armas_Ana"
        ,"Comédie,Crime,Drame"
        ,"kmXo1Ncy0aM");
    }

    public void ajout49(){
        ajouter("Braquage en or"
        ,"2021-06-03"
        ,"Richard Pace est un célèbre criminel. Il s\'envole pour le Moyen-Orient afin de voler de l\'or mais son implication va aller bien au-delà de ce qu\'il avait planifié avant son départ pour Abu Dhabi."
        ,"Angelo_Mike"
        ,"Action,Thriller"
        ,"xk8-GQlLwE8");
    }

    public void ajout50(){
        ajouter("Hashima project"
        ,"2013-10-31"
        ,"Hashima était autrefois l\'une des îles les plus peuplées, mais depuis 1974 elle est devenue une île fantôme. De nos jours, un groupe d\'ados va vouloir s\'y aventurer pour capturer des phénomènes paranormaux."
        ,"Angelo_Mike"
        ,"Horreur,Thriller"
        ,"4OuwZDNzlA0");
    }

    public void ajout51(){
        ajouter("Moi, député"
        ,"2012-08-09"
        ,"Lorsque le député chevronné Cam Brady commet une gaffe monumentale en public à l\'approche des élections, un tandem de PDG milliardaires entend bien en profiter pour placer leur candidat et étendre leur influence sur leur fief, en Caroline du Nord. Leur homme n\'est autre que le candide Marty Huggins qui dirige l\'office du tourisme du coin. Si, au départ, Marty ne semble pas le candidat idéal, il ne tarde pas à se révéler un redoutable concurrent pour le charismatique Cam grâce à l\'aide de ses bienfaiteurs, d\'un directeur de campagne sans vergogne et des relations de ses parents dans la politique. Alors que le jour du scrutin approche, les deux hommes s\'engagent dans un combat impitoyable : désormais, tous les coups sont permis entre Cam et Marty qui n\'hésitent plus à s\'insulter et à en venir aux mains dans un affrontement à mort."
        ,"LaNasa_Katherine"
        ,"Comédie"
        ,"24QO0zC41jw");
    }

    public void ajout52(){
        ajouter("Suspect"
        ,"2013-07-11"
        ,"Aux yeux de tous, Robert Hansen est un homme respecté et un père de famille attentionné. Ce que tout le monde ignore en revanche, c\'est que Robert Hansen, depuis 12 ans, kidnappe des jeunes femmes et abuse d\'elles avant de les lâcher en pleine nature pour les chasser et les abattre comme des vulgaires proies. Lorsque Cindy, une de ses victimes, parvient à lui échapper, elle se tourne vers l\'inspecteur Halcombe. Ce dernier, faute de preuves, va devoir faire l\'impossible pour tenter de coincer le redoutable tueur en série…"
        ,"LaNasa_Katherine"
        ,"Thriller,Crime"
        ,"r53aNKtKVLM");
    }

    public void ajout53(){
        ajouter("Valentine\'s Day"
        ,"2010-02-10"
        ,"Les destins croisés de couples qui se séparent ou se retrouvent, de célibataires qui se rencontrent à Los Angeles, le jour de Saint-Valentin..."
        ,"LaNasa_Katherine"
        ,"Comédie,Romance"
        ,"N3xYP_ygMFA");
    }

    public void ajout54(){
        ajouter("Seven"
        ,"1995-09-22"
        ,"À New York, un criminel anonyme a décidé de commettre 7 meurtres basés sur les 7 pêchés capitaux énoncés dans la Bible : gourmandise, avarice, paresse, orgueil, luxure, envie et colère. Vieux flic blasé à 7 jours de la retraite, l\'inspecteur Somerset mène l\'enquête tout en formant son remplaçant, l\'ambitieux inspecteur David Mills…"
        ,"Freeman_Morgan"
        ,"Crime,Mystère,Thriller"
        ,"5eYUMIjNOS4");
    }

    public void ajout55(){
        ajouter("Les Évadés"
        ,"1994-09-23"
        ,"En 1947, Andy Dufresne, un jeune banquier, est condamné à la prison à vie pour le meurtre de sa femme et de son amant. Ayant beau clamer son innocence, il est emprisonné à Shawshank, le pénitencier le plus sévère de l\'État du Maine. Il y fait la rencontre de Red, un noir désabusé, détenu depuis vingt ans. Commence alors une grande histoire d\'amitié entre les deux hommes…"
        ,"Freeman_Morgan,Brown_Clancy"
        ,"Drame,Crime"
        ,"2e8Otbbcowc");
    }

    public void ajout56(){
        ajouter("Lucy"
        ,"2014-07-25"
        ,"Lucy, une jeune étudiante ordinaire, se fait kidnapper. À son réveil, elle découvre que les membres d’une organisation criminelle lui ont inséré un paquet de drogue dans l’estomac dans le but de lui faire passer la frontière. Mais lorsqu’à la suite d’un coup porté à son ventre le produit se déverse dans son corps et s’implante dans son système, la jeune femme en subit les étonnants effets. Cette substance synthétique lui permet de décupler ses capacités intellectuelles et physiques. Devenue un génie autant qu’une véritable machine de guerre, Lucy dispose désormais de pouvoirs illimités et surhumains…"
        ,"Freeman_Morgan,Johansson_Scarlett"
        ,"Action,Science-Fiction"
        ,"7gPrNpHaFX8");
    }

    public void ajout57(){
        ajouter("Spider-Man : No Way Home"
        ,"2021-12-15"
        ,"Après les événements liés à l\'affrontement avec Mysterio, l\'identité secrète de Spider-Man a été révélée. Il est poursuivi par le gouvernement américain, qui l\'accuse du meurtre de Mysterio, et traqué par les médias. Cet événement a également des conséquences terribles sur la vie de sa petite-amie M.J. et de son meilleur ami Ned. Désemparé, Peter Parker demande alors de l\'aide au docteur Strange. Ce dernier lance un sort pour que tout le monde oublie que Peter est Spider-Man. Mais les choses ne se passent pas comme prévu, et cette action altère la stabilité de l\'espace-temps. Cela ouvre le « multivers », un concept terrifiant dont ils ne savent quasiment rien..."
        ,"undefined_Zendaya,Holland_Tom"
        ,"Action,Aventure,Science-Fiction"
        ,"7w_w10HVa54");
    }

    public void ajout58(){
        ajouter("Spider-Man : Homecoming"
        ,"2017-07-05"
        ,"Après ses spectaculaires débuts dans Captain America : Civil War, le jeune Peter Parker découvre peu à peu sa nouvelle identité, celle de Spider-Man, le super-héros lanceur de toile. Galvanisé par son expérience avec les Avengers, Peter rentre chez lui auprès de sa tante May, sous l’œil attentif de son nouveau mentor, Tony Stark. Il s’efforce de reprendre sa vie d’avant, mais au fond de lui, Peter rêve de se prouver qu’il est plus que le sympathique super héros du quartier. L’apparition d’un nouvel ennemi, le Vautour, va mettre en danger tout ce qui compte pour lui..."
        ,"undefined_Zendaya,Holland_Tom,Keaton_Michael"
        ,"Action,Aventure,Science-Fiction,Drame"
        ,"mJQ4u-kXoGc");
    }

    public void ajout59(){
        ajouter("Amants"
        ,"1991-02-23"
        ,"Un jeune homme va devoir faire appel aux économies de sa fiancée pour sauver sa maitresse de la ruine."
        ,"Escaño_Mabel"
        ,"Crime,Drame,Romance"
        ,"yJtrJZVCmoA");
    }

    public void ajout60(){
        ajouter("La mansión de los muertos vivientes"
        ,"1982-11-18"
        ,"Quatre amies, Candy, Mabel, Marta et Tina, serveuses dans un bar d\'Europe centrale se rendent en vacances hors saison sur une plage au sud de l’Espagne. L’hôtel est un lieu sinistre et Carlo son gérant un homme inquiétant qui séquestre sa femme, nue et enchainée comme un animal, dans une chambre de l\'établissement. Les jeunes femmes décident de visiter les ruines d\'une abbaye antique qui abrita jadis des membres des forces du Temple. Par les nuits de grand vent, un glas funèbre se fait entendre et les cadavres momifiés des templiers sortent de leur tombe pour célébrer des rites sanglants..."
        ,"Escaño_Mabel"
        ,"Horreur"
        ,"kYm27zxO1-Y");
    }

    public void ajout62(){
        ajouter("Midsommar"
        ,"2019-07-03"
        ,"Dani et Christian sont sur le point de se séparer quand la famille de Dani est touchée par une tragédie. Attristé par le deuil de la jeune femme, Christian ne peut se résoudre à la laisser seule et l’emmène avec lui et ses amis à un festival estival qui n’a lieu que tous les 90 ans et se déroule dans un village suédois isolé. Mais ce qui commence comme des vacances insouciantes dans un pays où le soleil ne se couche pas va vite prendre une tournure beaucoup plus sinistre et inquiétante."
        ,"Pugh_Florence"
        ,"Horreur,Drame,Mystère"
        ,"YMKeRDlcpJQ");
    }

    public void ajout63(){
        ajouter("Black Widow"
        ,"2021-07-07"
        ,"Natasha Romanoff, alias Black Widow, voit resurgir la part la plus sombre de son passé pour faire face à une redoutable conspiration liée à sa vie d’autrefois. Poursuivie par une force qui ne reculera devant rien pour l’abattre, Natasha doit renouer avec ses activités d’espionne et avec des liens qui furent brisés, bien avant qu’elle ne rejoigne les Avengers."
        ,"Pugh_Florence,Johansson_Scarlett"
        ,"Action,Aventure,Science-Fiction"
        ,"4l99M0zOEaA");
    }

    public void ajout64(){
        ajouter("Les Filles du docteur March"
        ,"2019-12-25"
        ,"Aux États-Unis, pendant la guerre de Sécession, 4 sœurs issues de la classe moyenne font face aux difficultés de la vie quotidienne en ce temps de conflit. La raisonnable Mege, l’intrépide Jo, la charitable Beth et l’orgueilleuse May vivent dans l’ombre protectrice et bienveillante de leur mère Marnee et de sa tante March. Mais la rencontre de leur voisin Laurie va transformer les premiers émois du bal des débutantes en véritables histoires d’amour…  Une nouvelle adaptation du classique de Louisa May Alcott."
        ,"Pugh_Florence"
        ,"Drame,Romance"
        ,"OniST4FKp9c");
    }

    public void ajout65(){
        ajouter("Comme Cendrillon 5 : Un conte de Noël"
        ,"2019-10-15"
        ,"Kat Decker aurait bien besoin d\'un miracle de Noël ! L\'aspirante chanteuse compositrice a de grands rêves... et des problèmes bien plus grands encore. Sa belle-mère vaniteuse la force à être une chanteuse déguisée en elfe pour le milliardaire Terrence. Toutefois, ce job a un point positif : Nick, le charmant Père Noël. À l\'approche du gala de Noël de Wintergarden, la belle-mère et les demi-soeurs de Kat sont déterminées à l\'empêcher de s\'y rendre. Est-ce que son chien fidèle, sa meilleure amie et la magie des fêtes de fin d\'année seront suffisants pour arranger les choses ?"
        ,"Phillips_Maddie"
        ,"Comédie,Musique,Romance,Fantastique"
        ,"fXr-TccSTSk");
    }

    public void ajout66(){
        ajouter("Avatar"
        ,"2009-12-15"
        ,"Un marine paraplégique, envoyé sur la lune Pandora pour une mission unique, est tiraillé entre suivre ses ordres et protéger le monde qu\'il considère dorénavant comme le sien."
        ,"Saldaña_Zoe"
        ,"Action,Aventure,Fantastique,Science-Fiction"
        ,"O1CzgULNRGs");
    }

    public void ajout67(){
        ajouter("Les Gardiens de la Galaxie"
        ,"2014-07-30"
        ,"Peter Quill est un aventurier traqué par tous les chasseurs de primes pour avoir volé un mystérieux globe convoité par le puissant Ronan, dont les agissements menacent l’univers tout entier. Lorsqu’il découvre le véritable pouvoir de ce globe et la menace qui pèse sur la galaxie, il conclut une alliance fragile avec quatre aliens disparates : Rocket, un raton laveur fin tireur, Groot, un humanoïde semblable à un arbre, l’énigmatique et mortelle Gamora, et Drax le Destructeur, qui ne rêve que de vengeance. En les ralliant à sa cause, il les convainc de livrer un ultime combat aussi désespéré soit‐il pour sauver ce qui peut encore l’être…"
        ,"Saldaña_Zoe,Pratt_Chris"
        ,"Action,Science-Fiction,Aventure"
        ,"ry19UYQoAro");
    }

    public void ajout68(){
        ajouter("Les Gardiens de la Galaxie Vol. 2"
        ,"2017-04-19"
        ,"Avec en toile de fond l’Awesome Mixtape #2, Les Gardiens de la Galaxie Vol. 2 poursuit les aventures de l’équipe alors qu’ils traversent les confins du cosmos. Les Gardiens doivent se battre pour que leur nouvelle famille reste ensemble tandis qu’ils cherchent à percer le mystère de la véritable filiation de Star-Lord. De vieux ennemis deviennent de nouveaux alliés et des personnages appréciés des fans, issus des comics, viennent en aide à nos héros alors que l’Univers Cinématographique Marvel continue de se développer."
        ,"Saldaña_Zoe,Pratt_Chris,Klementieff_Pom"
        ,"Science-Fiction,Aventure,Action"
        ,"e2LkglavLRs");
    }

    public void ajout69(){
        ajouter("La Liste de Schindler"
        ,"1993-12-15"
        ,"Évocation des années de guerre d’Oskar Schindler, fils d’industriel d’origine autrichienne rentré à Cracovie en 1939 avec les troupes allemandes. Il va, tout au long de la guerre, protéger des juifs en les faisant travailler dans sa fabrique et en 1944 sauver 800 hommes et 300 femmes du camp d’extermination d’Auschwitz‐Birkenau."
        ,"Neeson_Liam,Fiennes_Ralph"
        ,"Drame,Histoire,Guerre"
        ,"ONWtyxzl-GE");
    }

    public void ajout70(){
        ajouter("Star Wars, épisode I - La Menace fantôme"
        ,"1999-05-19"
        ,"Il y a bien longtemps, dans une galaxie très lointaine... La République connaît de nombreux tourments : la corruption fait vaciller ses bases, le Sénat s\'embourbe dans des discussions politiques sans fin et de nombreux pouvoirs dissidents commencent à émerger, annonçant la chute d\'un système autrefois paisible. Puissante et intouchable, la Fédération du Commerce impose par la force la taxation des routes commerciales. Refusant de céder, la pacifique planète Naboo, dirigée par la jeune Reine Amidala, subit un blocus militaire de la Fédération. Dépêchés par le Sénat pour régler cette affaire, les chevaliers Jedi Qui-Gon Jinn et Obi-Wan Kenobi découvrent qu\'une véritable offensive de la Fédération est imminente. Libérant la Reine et ses proches, ils quittent la planète mais doivent se poser sur Tatooine pour réparer leur vaisseau..."
        ,"Neeson_Liam"
        ,"Aventure,Action,Science-Fiction"
        ,"G9e3R-vkj_A");
    }

    public void ajout71(){
        ajouter("Taken"
        ,"2008-02-18"
        ,"Que peut-on imaginer de pire pour un père que d\'assister impuissant à l\'enlèvement de sa fille via un téléphone portable ? C\'est le cauchemar vécu par Bryan, ancien agent des services secrets américains, qui n\'a que quelques heures pour arracher Kim des mains d\'un redoutable gang spécialisé dans la traite des femmes. Premier problème à résoudre : il est à Los Angeles, elle vient de se faire enlever à Paris."
        ,"Neeson_Liam"
        ,"Action,Thriller"
        ,"UX_71dbzJIw");
    }

    public void ajout72(){
        ajouter("Dune"
        ,"2021-09-15"
        ,"L\'histoire de Paul Atreides, jeune homme aussi doué que brillant, voué à connaître un destin hors du commun qui le dépasse totalement. Car, s\'il veut préserver l\'avenir de sa famille et de son peuple, il devra se rendre sur Dune, la planète la plus dangereuse de l\'Univers. Mais aussi la seule à même de fournir la ressource la plus précieuse capable de décupler la puissance de l\'Humanité. Tandis que des forces maléfiques se disputent le contrôle de cette planète, seuls ceux qui parviennent à dominer leur peur pourront survivre…"
        ,"Ferguson_Rebecca,Chalamet_Timothée"
        ,"Science-Fiction,Aventure"
        ,"gHt8tCHbB2M");
    }

    public void ajout73(){
        ajouter("La Fille du train"
        ,"2016-10-05"
        ,"Rachel prend tous les jours le même train et passe toujours devant la même maison. Dévastée par son divorce, elle fantasme sur le couple qui y vit et leur imagine une vie parfaite… jusqu’au jour où elle est témoin d’un événement extrêmement choquant et se retrouve, malgré elle, étroitement mêlée à un angoissant mystère."
        ,"Ferguson_Rebecca"
        ,"Thriller,Mystère,Drame,Crime"
        ,"eA_8YvUktaE");
    }

    public void ajout74(){
        ajouter("Life : Origine Inconnue"
        ,"2017-03-22"
        ,"À bord de la Station Spatiale Internationale, les six membres d’équipage font l’une des plus importantes découvertes de l’histoire de l’humanité : la toute première preuve d’une vie extraterrestre sur Mars. Alors qu’ils approfondissent leurs recherches, leurs expériences vont avoir des conséquences inattendues, et la forme de vie révélée va s’avérer bien plus intelligente que ce qu’ils pensaient…"
        ,"Ferguson_Rebecca"
        ,"Horreur,Science-Fiction,Mystère,Thriller"
        ,"gySYa2luR8A");
    }

    public void ajout75(){
        ajouter("Very Bad Trip"
        ,"2009-06-02"
        ,"Ils avaient prévu un enterrement de vie de garçon à Las Vegas qu’ils n’oublieraient jamais. Maintenant, ils ont vraiment besoin de se souvenir de ce qu’il s’est passé. À qui est ce bébé dans le placard de leur suite au Caesar Palace ? Comment un tigre est‐il arrivé jusque dans leur salle de bain ? Pourquoi manque‐t‐il une dent à l’un d’entre eux ? Et, surtout, où est le marié ? ! Mais ce qu’ils ont fait la nuit précédente n’est rien en comparaison des combines scandaleuses qu’ils doivent mettre en œuvre pour tenter de rassembler les pièces du puzzle qui constituent leur nuit passée – à partir d’indices franchement brumeux…"
        ,"Harris_Rachael"
        ,"Comédie"
        ,"UBsx-rvu9Uk");
    }

    public void ajout76(){
        ajouter("Les Mondes de Ralph"
        ,"2012-11-01"
        ,"Dans une salle d’arcade, Ralph la casse est le héros mal aimé d’un jeu des années 80. Son rôle est simple : il casse tout ! Pourtant il ne rêve que d’une chose, être aimé de tous…  Vanellope Van Schweetz quant à elle, évolue dans un jeu de course, fabriqué uniquement de sucreries. Son gros défaut : être une erreur de programme, ce qui lui vaut d’être interdite de course et rejetée de tous…  Ces deux personnages n’auraient jamais dû se croiser… et pourtant, Ralph va bousculer les règles et voyager à travers les différents mondes de la salle d’arcade pour atteindre son but : prouver à tous qu’il peut devenir un héros… Ensemble, arriveront-ils à atteindre leurs rêves ?"
        ,"Harris_Rachael"
        ,"Familial,Animation,Comédie,Aventure"
        ,"igpNoKG_QO0");
    }

    
    public void ajout79(){
        ajouter("Underworld"
        ,"2003-09-19"
        ,"Depuis des centaines d\'années, à l\'insu des humains, se déroule un combat sans merci entre deux races immortelles : vampires contre lycans, un peuple sauvage proche des loups-garous de nos légendes… Selene , une intrépide vampire, mène cette lutte millénaire sans aucune pitié pour ses ennemis. Mais lorsque Michael Corvin, un humain, se retrouve par hasard au cœur de cette guerre des clans, les règles changent. Selene entre passion et fidélité, va devoir choisir son camp."
        ,"Beckinsale_Kate"
        ,"Fantastique,Action,Thriller"
        ,"vh9OegrXsn8");
    }

    public void ajout80(){
        ajouter("Underworld : Nouvelle ère"
        ,"2012-01-19"
        ,"Depuis des siècles, Lycans et vampires se livrent une bataille sans merci. Mais les deux races sont à l’aube d’une ère nouvelle car les humains, qui ont récemment découvert leur existence, décident de cesser leurs conflits internes pour s’engager ensemble dans la lutte contre ce qu’ils considèrent comme des fléaux. Selene s’attire la convoitise de l’armée et des scientifiques. Une traque incessante commence alors contre la plus redoutable des vampires."
        ,"Beckinsale_Kate"
        ,"Fantastique,Action,Horreur"
        ,"T5rhDuzpEwk");
    }

    public void ajout81(){
        ajouter("Underworld 2 : Évolution"
        ,"2006-01-12"
        ,"La lutte millénaire que se livrent vampires et lycans est sur le point de connaître un tournant décisif… Pour avoir découvert le secret du massacre de sa famille, Selene, la redoutable guerrière vampire, est plus que jamais seule et menacée. Ses véritables ennemis ne sont pas forcément ceux qu\'elle croyait. Michael, devenu le premier hybride à la fois vampire et lycan, aimerait se joindre à elle, mais il est incapable de contrôler la part lycan qui fait rage en lui. Pour chacun d\'eux, il est temps de percer le mystère de leurs origines et de la guerre, mais dans cette quête de vérité, ils devront affronter les plus puissants des adversaires, les plus proches aussi... Plus que jamais, au plus profond des ténèbres, loin du regard des hommes, se joue le sort du monde..."
        ,"Beckinsale_Kate"
        ,"Fantastique,Action,Thriller"
        ,"v86oFYYz3Bg");
    }

    public void ajout82(){
        ajouter("Hunger Games"
        ,"2012-03-12"
        ,"Dans un proche futur, les États‐Unis sont devenus un gouvernement fédéral dystopique dans lequel chacun des 12 districts doit envoyer un garçon et une fille combattre lors d’un événement annuel télévisé, « Les Hunger Games ». La seule issue à ces jeux pour les « Tributs » est de tuer ou d’être tué. Lorsque la petite sœur de Katniss est tirée au sort pour y participer, celle‐ci décide de prendre sa place."
        ,"Lawrence_Jennifer,Hutcherson_Josh"
        ,"Science-Fiction,Aventure,Fantastique"
        ,"T_OUuZBWRiE");
    }

    public void ajout83(){
        ajouter("Hunger Games - L\'embrasement"
        ,"2013-11-15"
        ,"Après les premiers Hunger Games, Katniss est devenue le symbole de la rébellion. Le peuple de Panem est impatient de la retrouver pour la grande Tournée de la victoire. Mais Katniss va devoir prouver au Capitole et au Président Snow que ses sentiments envers Peeta étaient sincères afin de sauver ceux qu’elle aime…"
        ,"Lawrence_Jennifer,Hutcherson_Josh"
        ,"Aventure,Action,Science-Fiction"
        ,"-ZcW_6i2Rkg");
    }

    public void ajout84(){
        ajouter("Hunger Games - La Révolte, 1ère partie"
        ,"2014-11-19"
        ,"Katniss Everdeen s’est réfugiée dans le District 13 après avoir détruit à jamais l’arène et les Jeux. Sous le commandement de la Présidente Coin, chef du district, et suivant les conseils de ses amis en qui elle a toute confiance, Katniss déploie ses ailes pour devenir le symbole de la rébellion. Elle va se battre pour sauver Peeta et libérer le pays tout entier, à qui son courage a redonné espoir."
        ,"Lawrence_Jennifer,Hutcherson_Josh"
        ,"Science-Fiction,Aventure,Thriller"
        ,"zdMIft77FEY");
    }

    public void ajout85(){
        ajouter("Les Incorruptibles"
        ,"1987-06-03"
        ,"À Chicago en 1930, à l’époque de la prohibition, le trafic d’alcool bat son plein et permet à des truands de bâtir des fortunes colossales. Eliot Ness, jeune policier frais émoulu du FBI, est chargé de démanteler un réseau de contrebande d’alcool. L’ennemi est facilement identifiable : Al Capone, inattaquable officiellement, est pourtant impliqué dans les affaires les plus sordides. La première mission de Ness, maladroitement conduite, se solde par un échec humiliant. Loin de se laisser décourager par ce premier affront, Ness décide de s’entourer de personnes de confiance. Il recrute ainsi Jim Malone, un flic qui connaît bien le monde des truands…"
        ,"Costner_Kevin"
        ,"Crime,Drame,Histoire,Thriller"
        ,"Q-PIxvJLjZc");
    }

    public void ajout86(){
        ajouter("Danse avec les loups"
        ,"1990-03-30"
        ,"Durant la guerre de Sécession, le lieutenant John Dunbar, envoyé à un poste de reconnaissance dans les plaines du Dakota, rencontre le peuple sioux. Il se lie d\'amitié avec la population indienne, au point d\'intégrer une tribu et de s\'éprendre de l\'une des leurs."
        ,"Costner_Kevin,McDonnell_Mary"
        ,"Aventure,Drame,Western"
        ,"VWDYpL1Y0ng");
    }

    public void ajout87(){
        ajouter("Waterworld"
        ,"1995-07-28"
        ,"À la suite d\'une catastrophe écologique, la Terre est recouverte par les océans. Les rares survivants vivent sur des atolls artificiels, rêvant d\'une contrée mythique, Dryland, recouverte de vastes forêts et de profondes vallées."
        ,"Costner_Kevin"
        ,"Aventure,Action,Science-Fiction"
        ,"IiZRKscBKa8");
    }

    public void ajout88(){
        ajouter("Cruella"
        ,"2021-05-26"
        ,"Londres, années 70, en plein mouvement punk rock. Escroc pleine de talent, Estella est résolue à se faire un nom dans le milieu de la mode. Elle se lie d’amitié avec deux jeunes vauriens qui apprécient ses compétences d’arnaqueuse et mène avec eux une existence criminelle dans les rues de Londres. Un jour, ses créations se font remarquer par la baronne von Hellman, une grande figure de la mode, terriblement chic et horriblement snob. Mais leur relation va déclencher une série de révélations qui amèneront Estella à se laisser envahir par sa part sombre, au point de donner naissance à l’impitoyable Cruella, une brillante jeune femme assoiffée de mode et de vengeance…"
        ,"Stone_Emma"
        ,"Comédie,Crime"
        ,"VKbJsznd7pg");
    }

    public void ajout89(){
        ajouter("The Amazing Spider-Man"
        ,"2012-06-23"
        ,"Abandonné par ses parents lorsqu’il était enfant, Peter Parker a été élevé par son oncle Ben et sa tante May. Il est aujourd’hui au lycée, mais il a du mal à s’intégrer. Comme la plupart des adolescents de son âge, Peter essaie de comprendre qui il est et d’accepter son parcours. Amoureux pour la première fois, lui et Gwen Stacy découvrent les sentiments, l’engagement et les secrets. En retrouvant une mystérieuse mallette ayant appartenu à son père, Peter entame une quête pour élucider la disparition de ses parents, ce qui le conduit rapidement à Oscorp et au laboratoire du docteur Curt Connors, l’ancien associé de son père. Spider-Man va bientôt se retrouver face au Lézard, l’alter ego de Connors. En décidant d’utiliser ses pouvoirs, il va choisir son destin…"
        ,"Stone_Emma"
        ,"Action,Aventure,Fantastique"
        ,"mJlDpguadbk");
    }

    public void ajout90(){
        ajouter("La La Land"
        ,"2016-11-29"
        ,"Au cœur de Los Angeles, une actrice en devenir prénommée Mia sert des cafés entre deux auditions. De son côté, Sebastian, passionné de jazz, joue du piano dans des clubs miteux pour assurer sa subsistance. Tous deux sont bien loin de la vie rêvée à laquelle ils aspirent… Le destin va réunir ces doux rêveurs, mais leur coup de foudre résistera-t-il aux tentations, aux déceptions, et à la vie trépidante d’Hollywood ?"
        ,"Stone_Emma,Gosling_Ryan"
        ,"Comédie,Drame,Romance,Musique"
        ,"vOBtJWG_KlI");
    }

    public void ajout95(){
        ajouter("Pearl"
        ,"2022-09-16"
        ,"Piégée sur la ferme familiale et isolée de tout, Pearl doit prendre soin de son père malade sous le contrôle autoritaire de sa mère, une femme stricte et pieuse. Attirée par le monde séduisant des films qu’elle écoute sans retenue, les ambitions de Pearl, ses tentations et ses répressions s’entrechoquent dans ce prologue racontant les origines de l’iconique vilaine de X."
        ,"Goth_Mia"
        ,"Horreur,Drame,Thriller"
        ,"UJSseBDe-4o");
    }

    public void ajout96(){
        ajouter("Spider-Man : Far From Home"
        ,"2019-06-28"
        ,"Peter et ses amis passent leurs vacances d’été en Europe. Mais ils n’auront pas vraiment l’occasion de se reposer puisque Peter accepte d’aider Nick Fury pour débusquer les mystérieuses créatures qui sont la cause des catastrophes naturelles qui frappent le continent."
        ,"Holland_Tom"
        ,"Action,Aventure,Science-Fiction"
        ,"FguIk-SEkWI");
    }

    public void ajout97(){
        ajouter("Cars : Quatre roues"
        ,"2006-06-08"
        ,"Un champion de course, Flash McQueen vit à cent à l\'heure jusqu’à ce qu’un détour le mène à Radiator Springs, une petite ville oubliée sur la Route 66. Il y rencontre Sally, Martin, Doc Hudson et un tas de personnages amusants qui l’aident à découvrir que la vie vaut plus que les trophées et la célébrité."
        ,"Wilson_Owen,Piven_Jeremy"
        ,"Animation,Aventure,Comédie,Familial"
        ,"5pOLY8BtH0g");
    }

    public void ajout98(){
        ajouter("Minuit à Paris"
        ,"2011-05-11"
        ,"Un jeune couple d’américains dont le mariage est prévu à l’automne se rend pour quelques jours à Paris. La magie de la capitale ne tarde pas à opérer, tout particulièrement sur le jeune homme amoureux de la Ville-lumière et qui aspire à une autre vie que la sienne."
        ,"Wilson_Owen"
        ,"Fantastique,Comédie,Romance"
        ,"ef47VO9E6xE");
    }

    public void ajout99(){
        ajouter("Cars 3"
        ,"2017-06-15"
        ,"Dépassé par une nouvelle génération de coureurs auto emmenés par l\'arrogant Jackson Storm, le légendaire Flash McQueen est soudain mis sur le bas-côté. Pour revenir dans la course, il aura besoin de la jeune technicienne Cruz Ramirez, de l\'inspiration du fabuleux Hudson Hornet et de quelques rebondissements inattendus et tours du destin."
        ,"Wilson_Owen"
        ,"Animation,Aventure,Comédie,Familial"
        ,"UPe4x4dhtIc");
    }

    public void ajout100(){
        ajouter("Inception"
        ,"2010-07-15"
        ,"Dom Cobb est un voleur expérimenté, le meilleur dans l\'art dangereux de l\'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l\'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l\'espionnage industriel alors qu\'il devient fugitif en perdant tout ce qu\'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s\'il parvient à accomplir l\'impossible inception."
        ,"DiCaprio_Leonardo,Murphy_Cillian,Nolan_Christopher"
        ,"Action,Science-Fiction,Aventure"
        ,"CPTIgILtna8");
    }

    public void ajout101(){
        ajouter("Titanic"
        ,"1997-11-18"
        ,"Southampton, 10 avril 1912. Le paquebot le plus grand et le plus moderne du monde, réputé pour son insubmersibilité, le « Titanic », appareille pour son premier voyage. Quatre jours plus tard, il heurte un iceberg. À son bord, un artiste pauvre et une grande bourgeoise tombent amoureux."
        ,"DiCaprio_Leonardo,Winslet_Kate"
        ,"Drame,Romance"
        ,"Quf4qIkD3KY");
    }

    public void ajout102(){
        ajouter("Shutter Island"
        ,"2010-02-14"
        ,"En 1954, le marshal Teddy Daniels et son coéquipier Chuck Aule sont envoyés enquêter sur l’île de Shutter Island, dans un hôpital psychiatrique où sont internés de dangereux criminels. L’une des patientes, Rachel Solando, a inexplicablement disparu. Comment la meurtrière a‐t‐elle pu sortir d’une cellule fermée de l’extérieur ? Le seul indice retrouvé dans la pièce est une feuille de papier sur laquelle on peut lire une suite de chiffres et de lettres sans signification apparente. Œuvre cohérente d’une malade, ou cryptogramme ?"
        ,"DiCaprio_Leonardo,Scorsese_Martin"
        ,"Drame,Thriller,Mystère"
        ,"Hz0ToXuAxag");
    }

    public void ajout103(){
        ajouter("Coraline"
        ,"2009-02-05"
        ,"Coraline Jones est une fillette intrépide et douée d’une curiosité sans limites. Ses parents, qui ont tout juste emménagé avec elle dans une étrange maison, n’ont guère de temps à lui consacrer. Pour tromper son ennui, Coraline décide donc de jouer les exploratrices. Ouvrant une porte condamnée, elle pénètre dans un appartement identique au sien… mais où tout est différent. Dans cet Autre Monde, chaque chose lui paraît plus belle, plus colorée et plus attrayante. Son Autre Mère est pleinement disponible, son Autre Père prend la peine de lui mitonner des plats exquis, et même le Chat, si hautain dans la Vraie vie, daigne s’entretenir avec elle. Coraline est bien tentée d’élire domicile dans ce Monde merveilleux, qui répond à toutes ses attentes. Mais le rêve va très vite tourner au cauchemar. Prisonnière de l’Autre Mère, Coraline va devoir déployer des trésors de bravoure, d’imagination et de ténacité pour rentrer chez elle et sauver sa Vraie famille…"
        ,"Fanning_Dakota"
        ,"Animation,Familial,Fantastique"
        ,"8CC7-5sYJv8");
    }

    public void ajout104(){
        ajouter("La Guerre des mondes"
        ,"2005-06-28"
        ,"Ray Ferrier est un docker divorcé et un père rien moins que parfait, qui n’entretient plus que des relations épisodiques avec son fils Robbie, 17 ans, et sa fille Rachel, 11 ans. Quelques minutes après que son ex‐femme et l’époux de cette dernière lui ont confié la garde des enfants, un puissant orage éclate. Ray assiste alors à un spectacle qui bouleversera à jamais sa vie…"
        ,"Fanning_Dakota"
        ,"Aventure,Thriller,Science-Fiction"
        ,"JLDflS8FkOI");
    }

    public void ajout105(){
        ajouter("Man on Fire"
        ,"2004-04-23"
        ,"Le Mexique est en proie à une vague d\'enlèvements sans précédent. Face au danger, certaines familles aisées engagent des gardes du corps pour assurer la protection rapprochée de leurs enfants.C\'est dans ce contexte lourd de menaces que débarque à Mexico l\'ancien agent de la CIA John Creasy. Appelé par son vieil ami Rayburn, ce dernier se voit proposer un job inattendu : bodyguard de la petite Pita Ramos, fille de l\'industriel Samuel Ramos.La fillette, précoce, pleine de curiosité et de vitalité, insupporte John par ses questions personnelles. Pourtant, au fil des jours, Pita parvient à percer ses défenses. Après bien des années, celui-ci retrouve le goût de vivre.C\'est alors que Pita est kidnappée. Bien que grièvement blessé, Creasy se lance à la poursuite des ravisseurs. Inflexible, il remonte la piste, se jurant de retrouver sa protégée."
        ,"Fanning_Dakota"
        ,"Action,Drame,Thriller"
        ,"eZoJ6w_0dQY");
    }

    public void ajout106(){
        ajouter("Seul sur Mars"
        ,"2015-09-30"
        ,"Au cours d’une mission spatiale habitée sur Mars, et à la suite d’un violent orage, l’astronaute Mark Watney est laissé pour mort et abandonné sur place par son équipage. Mais Watney a survécu et se retrouve seul sur cette planète hostile. Avec de maigres provisions, il ne doit compter que sur son ingéniosité, son bon sens et son intelligence pour survivre et trouver un moyen d’alerter la Terre qu’il est encore vivant. À des millions de kilomètres de là, la NASA et une équipe de scientifiques internationaux travaillent sans relâche pour ramener « le Martien » sur terre, pendant que, en parallèle, ses coéquipiers tentent secrètement d’organiser une audacieuse voire impossible mission de sauvetage."
        ,"Damon_Matt,Mara_Kate"
        ,"Drame,Aventure,Science-Fiction"
        ,"l7NUPqUoP0I");
    }

    public void ajout107(){
        ajouter("Will Hunting"
        ,"1997-12-05"
        ,"Will Hunting est un authentique génie mais également un rebelle aux élans imprévisibles. Il est né dans le quartier populaire de South Boston et a arrêté très tôt ses études, refusant le brillant avenir que pouvait lui procurer son intelligence. Il vit désormais entouré d\'une bande de copains et passe son temps dans les bars a chercher la bagarre et à commettre quelques petits délits qui risquent bien de l\'envoyer en prison. C\'est alors que ses dons prodigieux en mathématiques attirent l\'attention du professeur Lambeau, du Massachusetts Institute of Technology..."
        ,"Damon_Matt,Hauser_Cole"
        ,"Drame"
        ,"f3v9pRa5dR8");
    }

    public void ajout108(){
        ajouter("La Mémoire dans la peau"
        ,"2002-06-14"
        ,"Un homme entre la vie et la mort est tiré de l’océan par un bateau de pêche italien. Ayant retrouvé ses esprits, il se rend compte qu’il souffre d’amnésie totale et qu’il n’a sur lui aucun papier d’identité. Les nombreux talents qu’il se découvre, techniques de combat et connaissances de plusieurs langues, lui suggèrent un passé dangereux. Aidé de la d’abord récalcitrante Marie, il s’engage dans une recherche désespérée pour retrouver son identité et la raison qui pousse d’impitoyables assassins à le traquer."
        ,"Damon_Matt"
        ,"Action,Drame,Mystère,Thriller"
        ,"zTRMFgZK4oE");
    }

    public void ajout109(){
        ajouter("Le Chat Potté 2 : la dernière quête"
        ,"2022-12-07"
        ,"Le Chat Potté découvre que sa passion pour l\'aventure et son mépris du danger ont fini par lui coûter cher : il a épuisé huit de ses neuf vies, et en a perdu le compte au passage. Afin de retomber sur ses pattes notre héros velu se lance littéralement dans la quête de sa vie. Il s\'embarque dans une aventure épique aux confins de la Forêt Sombre afin de dénicher la mythique Etoile à vœu, seule susceptible de lui rendre ses vies perdues. Mais quand il ne vous en reste qu’une, il faut savoir faire profil bas, se montrer prudent et demander de l’aide. C’est ainsi qu’il se tourne vers son ancienne partenaire et meilleure ennemie de toujours : l’ensorcelante Kitty Pattes De Velours. Le Chat Potté et la belle Kitty vont être aidés dans leur quête, à leur corps bien défendant, par Perro, un corniaud errant et galleux à la langue bien pendue et d’une inaltérable bonne humeur."
        ,"Banderas_Antonio"
        ,"Aventure,Fantastique,Animation,Comédie,Familial,Action"
        ,"5fZ5hVKw2tg");
    }

    public void ajout111(){
        ajouter("Le Masque de Zorro"
        ,"1998-07-16"
        ,"Après vingt ans de prison, Don Diego de La Vega, alias Zorro, qui a autrefois combattu avec succès l\'oppression espagnole et qui est toujours poursuivi par la haine du gouverneur Rafael Montero, se cherche un successeur. Il rencontre alors un jeune brigand, Alejandro Murieta, qui a lui aussi quelques comptes a régler avec l\'ancien gouverneur. Après une formation complète, de La Vega remet a son élève le masque de Zorro, son épée et son fouet et l\'envoie déjouer le sinistre complot de Montero visant a confisquer la Californie au Mexique."
        ,"Banderas_Antonio"
        ,"Action,Aventure"
        ,"y0PZFlLD5Ik");
    }

    public void ajout112(){
        ajouter("Equalizer"
        ,"2014-09-24"
        ,"McCall, un homme qui pense avoir rangé son passé mystérieux derrière lui, se consacre à sa nouvelle vie tranquille. Au moment où il rencontre Teri, une jeune fille sous le contrôle de gangsters russes violents, il décide d\'agir. McCall sort ainsi de sa retraite et voit son désir de justice réveillé."
        ,"Washington_Denzel"
        ,"Thriller,Action,Crime"
        ,"Ydmk1_1_Gi4");
    }

    public void ajout113(){
        ajouter("Le Livre d\'Éli"
        ,"2010-01-14"
        ,"Dans un futur proche, l\'Amérique n\'est plus qu\'une terre désolée dont les villes sont des ruines et les routes autant de pièges infestés de bandes criminelles. Depuis des années, Eli voyage seul, se protégeant des attaques et se battant pour trouver de quoi survivre. Lorsqu\'il arrive dans ce qui fut autrefois la Californie, Eli se heurte au redoutable Carnegie, un homme qui ne recule devant rien pour imposer sa volonté à la petite communauté qu\'il contrôle. Eli fait aussi la connaissance de la très belle Solara et découvre que Carnegie compte bien étendre sa sombre domination à toute la région. Eli parvient à échapper de Carnegie, mais Solara l\'a suivi... Même s\'il est décidé à poursuivre sa route en solitaire, Eli comprend qu\'il ne peut abandonner la jeune femme. Pour elle, il va prendre des risques qu\'il n\'a jamais pris pour lui-même."
        ,"Washington_Denzel"
        ,"Action,Thriller,Science-Fiction"
        ,"l4aGAqlfIMo");
    }

    public void ajout114(){
        ajouter("Flight"
        ,"2012-11-02"
        ,"Après une nuit à faire la fête avec une hôtesse, le pilote de ligne Whip Whitaker prend les commandes de son appareil, pour un vol à destination d\'Atlanta. Il passe une partie du trajet à dormir, et est réveillé peu de temps avant l\'atterrissage. C\'est alors qu\'un incident survient: les gouvernes de l\'avion se bloquent. Au prix d\'une manœuvre risquée, Whitaker parvient à se poser en catastrophe. Lorsqu\'il se réveille à l\'hôpital, on lui apprend qu\'il a sauvé des dizaines de vies. Cependant, très vite, l\'enquête sur l\'accident révèle que Whitaker était sous l\'emprise de l\'alcool. Il risque une condamnation pour usage de drogue et homicide."
        ,"Washington_Denzel,Reilly_Kelly"
        ,"Drame"
        ,"LukGlsEh86k");
    }

    public void ajout115(){
        ajouter("Kill Bill: Volume 1"
        ,"2003-10-10"
        ,"Au cours d\'un mariage, un commando armé massacre l\'assistance, laissant pour morte la Mariée et l\'enfant qu\'elle porte. Après 4 ans de coma, la Mariée se lance à la poursuite des assassins, membres du clan de Bill, au sein duquel, sous le pseudonyme de Black Mamba, elle exerça elle-même autrefois ses talents de redoutable tueuse..."
        ,"Thurman_Uma"
        ,"Action,Crime"
        ,"2qYb8H9IrvY");
    }

    public void ajout116(){
        ajouter("Kill Bill: Volume 2"
        ,"2004-04-16"
        ,"Après avoir éliminé ses deux anciennes collègues Vernita Green et O-Ren Ishii, la Mariée, alias Black Mamba, poursuit l\'éradication du gang des Vipères Assassines. Prochaines cibles : Budd, puis Elle Driver, avant d\'atteindre enfin son but ultime : tuer leur chef, Bill..."
        ,"Thurman_Uma"
        ,"Action,Crime,Thriller"
        ,"WTt8cCIvGYI");
    }

    public void ajout117(){
        ajouter("Pulp Fiction"
        ,"1994-09-10"
        ,"L’odyssée sanglante et burlesque de petits malfrats dans la jungle d’Hollywood : Deux petits tueurs, un dangereux gangster marié à une camée, un boxeur roublard, des prêteurs sur gages sadiques, un caïd élégant et dévoué, un dealer bon mari et deux tourtereaux à la gâchette facile."
        ,"Thurman_Uma,L. Jackson_Samuel,Travolta_John"
        ,"Thriller,Crime"
        ,"gjAJnzTPltc");
    }

    public void ajout118(){
        ajouter("Love hard"
        ,"2021-11-05"
        ,"Une jeune femme, vivant à Los Angeles, tombe amoureuse d\'un charmant jeune homme, rencontré sur une application. Elle décide de lui faire une surprise pour Noël et de lui rendre visite sur la côte Est des États-Unis, où il réside, mais se rend compte, à son arrivée en ville, qu\'elle a été piégée et qu\'elle communique depuis le début avec un usurpateur. Lorsqu\'elle découvre que le véritable objet de son affection vit dans la même ville, celui qui a créé un faux compte sur l\'application pour la séduire promet de tout faire pour leur organiser une rencontre... si elle accepte de faire semblant d\'être sa petite amie durant les fêtes."
        ,"Dobrev_Nina"
        ,"Comédie,Romance"
        ,"WdG6voWaDaU");
    }

    public void ajout119(){
        ajouter("Le Monde de Charlie"
        ,"2012-09-20"
        ,"Au lycée où il vient d’arriver, on trouve Charlie bizarre. Sa sensibilité et ses goûts sont en décalage avec ceux de ses camarades de classe. Pour son prof de Lettres, c’est sans doute un prodige, pour les autres, c’est juste un « loser ». En attendant, il reste en marge – jusqu’au jour où deux terminales, Patrick et la jolie Sam, le prennent sous leur aile. Grâce à eux, il va découvrir la musique, les fêtes, le sexe… pour Charlie, un nouveau monde s’offre à lui."
        ,"Dobrev_Nina"
        ,"Drame"
        ,"e-edyZBUyNk");
    }

    public void ajout120(){
        ajouter("Wonder Woman"
        ,"2017-05-30"
        ,"Avant d\'être Wonder Woman, elle s\'appelait Diana, princesse des Amazones, entraînée pour être une guerrière impossible à conquérir. Elle est élevée sur une île isolée et paradisiaque, mais lorsqu\'un pilote américain s\'écrase sur leur rivage et annonce qu\'un conflit à grande échelle fait rage dans le monde, Diana quitte son foyer, convaincue qu\'elle doit arrêter cette menace. Combattant aux côtés de cet homme et des siens pour mettre fin à cette guerre et à toutes les guerres, Diana découvre ses vrais pouvoirs... Et son véritable destin."
        ,"Gadot_Gal"
        ,"Action,Aventure,Fantastique"
        ,"CWe308Fpywg");
    }

    public void ajout121(){
        ajouter("Wonder Woman 1984"
        ,"2020-12-16"
        ,"Suite des aventures de Diana Prince, alias Wonder Woman, Amazone devenue une super-héroïne dans notre monde. Après la Première guerre mondiale, direction les années 80 ! Cette fois, Wonder Woman doit affronter deux nouveaux ennemis, particulièrement redoutables : Max Lord et Cheetah."
        ,"Gadot_Gal"
        ,"Action,Aventure,Fantastique"
        ,"w1tjGOTrPZ8");
    }

    public void ajout122(){
        ajouter("Batman v Superman : L\'Aube de la Justice"
        ,"2016-03-23"
        ,"Craignant que Superman n’abuse de sa toute‐puissance, le Chevalier noir décide de l’affronter : le monde a‐t‐il davantage besoin d’un super‐héros aux pouvoirs sans limite ou d’un justicier à la force redoutable mais d’origine humaine ? Pendant ce temps‐là, une terrible menace se profile à l’horizon…"
        ,"Gadot_Gal,Cavill_Henry"
        ,"Action,Aventure,Fantastique"
        ,"rW4ZaR2Jndg");
    }

    public void ajout123(){
        ajouter("I Wish : Faites un vœu"
        ,"2017-07-07"
        ,"Pas facile de survivre à l\'enfer du lycée, Claire Shannon et ses copines en savent quelque chose. Du coup, quand son père lui offre une ancienne boîte à musique dont les inscriptions promettent d\'exaucer tous ses vœux, Claire tente sa chance. Et ça marche ! Argent, popularité, petit ami, tout semble parfait. Mais le rêve a un prix : au fur et à mesure de ses souhaits, des personnes de son entourage meurent dans des conditions particulièrement atroces. Claire le sait : elle doit se débarrasser de la boîte pour sauver sa vie et celle de ses proches avant de faire le voeu de trop."
        ,"Revord_Raegan"
        ,"Horreur,Thriller,Fantastique"
        ,"0_pr1Re5wf0");
    }

    public void ajout124(){
        ajouter("28 jours plus tard"
        ,"2002-10-31"
        ,"Un commando de la Protection Animale fait irruption dans un laboratoire top secret pour délivrer des dizaines de chimpanzés soumis à de terribles expériences. Mais aussitôt libérés, les primates, contaminés par un mystérieux virus et animés d’une rage incontrôlable, bondissent sur leurs « sauveurs » et les massacrent. 28 jours plus tard, le mal s’est répandu à une vitesse fulgurante à travers le pays, la population a été évacuée en masse et Londres n’est plus qu’une ville fantôme. Les rares rescapés se terrent pour échapper aux « Contaminés » assoiffés de violence. C’est dans ce contexte que Jim, un coursier, sort d’un profond coma…"
        ,"Murphy_Cillian"
        ,"Horreur,Thriller,Science-Fiction"
        ,"oPvUhUrst20");
    }

    public void ajout125(){
        ajouter("Star Wars, épisode III - La Revanche des Sith"
        ,"2005-05-17"
        ,"La Guerre des Clones fait rage. Une franche hostilité oppose désormais le Chancelier Palpatine au Conseil Jedi. Anakin Skywalker, jeune Chevalier Jedi pris entre deux feux, hésite sur la conduite à tenir. Séduit par la promesse d\'un pouvoir sans précédent, tenté par le côté obscur de la Force, il prête allégeance au maléfique Darth Sidious et devient Dark Vador. Les Seigneurs Sith s\'unissent alors pour préparer leur revanche, qui commence par l\'extermination des Jedi. Seuls rescapés du massacre, Yoda et Obi-Wan se lancent à la poursuite des Sith. La traque se conclut par un spectaculaire combat au sabre entre Anakin et Obi-Wan, qui décidera du sort de la galaxie..."
        ,"Christensen_Hayden"
        ,"Aventure,Action,Science-Fiction"
        ,"t1qtvKYwTV0");
    }

    public void ajout126(){
        ajouter("Star Wars, épisode II - L\'Attaque des clones"
        ,"2002-05-15"
        ,"Depuis le blocus de la planète Naboo par la Fédération du commerce, la République, gouvernée par le Chancelier Palpatine, connaît une véritable crise. Un groupe de dissidents, mené par le sombre Jedi comte Dooku, manifeste son mécontentement envers le fonctionnement du régime. Le Sénat et la population intergalactique se montrent pour leur part inquiets face à l\'émergence d\'une telle menace.Certains sénateurs demandent à ce que la République soit dotée d\'une solide armée pour empêcher que la situation ne se détériore davantage. Parallèlement, Padmé Amidala, devenue sénatrice, est menacée par les séparatistes et échappe de justesse à un attentat. Le Padawan Anakin Skywalker est chargé de sa protection. Son maître, Obi-Wan Kenobi, part enquêter sur cette tentative de meurtre et découvre la constitution d\'une mystérieuse armée de clones..."
        ,"Christensen_Hayden"
        ,"Aventure,Action,Science-Fiction"
        ,"1D7DUNCHXkg");
    }

    public void ajout127(){
        ajouter("Jumper"
        ,"2008-02-13"
        ,"Depuis qu\'il a découvert qu\'il pouvait se téléporter n\'importe où sur terre, le monde n\'a plus de limite pour David Rice. Grâce à son pouvoir, il peut déjeuner en Égypte sur la tête du Sphinx, passer la journée à faire du surf en Australie, dîner à Paris et prendre le dessert au Japon. Les murs ne l\'arrêtent plus et aucun coffre de banque ne lui résiste. Libre comme personne, David vit dans l\'insouciance la plus totale, jusqu\'à ce que..."
        ,"Christensen_Hayden"
        ,"Action,Aventure,Science-Fiction"
        ,"RcMH5sAYc5o");
    }

    public void ajout128(){
        ajouter("Fear Street Partie 2 : 1978"
        ,"2021-07-08"
        ,"En 1978, le camp de vacances Nightwing est divisé entre les campeurs et les moniteurs de la ville prospère de Sunnyvale et les campeurs et le personnel d\'entretien de la ville défavorisée de Shadyside. Mais, lorsque des horreurs de l\'histoire commune des deux petites villes prennent vie, ils doivent s\'unir afin de résoudre un mystère terrifiant, avant qu\'il ne soit trop tard."
        ,"Rudd_Emily"
        ,"Mystère,Horreur"
        ,"eR2KSY1fipo");
    }

    public void ajout129(){
        ajouter("Fear Street Partie 3 : 1666"
        ,"2021-07-14"
        ,"En 1666, une ville coloniale est en prise à une chasse aux sorcières hystérique, qui aura des conséquences mortelles pour les siècles à venir. Il appartient aux adolescents de 1994 d\'essayer de mettre fin à la malédiction qui sévit dans leur petite ville, avant qu\'il ne soit trop tard."
        ,"Rudd_Emily"
        ,"Mystère,Horreur"
        ,"dj3CXY8rKuY");
    }

    public void ajout130(){
        ajouter("The Creator"
        ,"2023-09-27"
        ,"Dans un futur proche, les humains et l\'intelligence artificielle (IA) se livrent une guerre sans merci. Joshua, un ex-agent des forces spéciales fragilisé par la disparition de sa femme, est recruté pour traquer et neutraliser le Créateur, l\'insaisissable architecte d\'une IA avancée à l\'origine d\'une arme qui pourrait mettre fin à la guerre... et détruire l\'humanité tout entière. Avec l\'aide d\'une unité d\'agents d\'élite, Joshua traverse les lignes ennemies et pénètre au coeur de leur dangereux territoire. Il découvrira bientôt que l\'arme funeste qu\'il est chargé de détruire n\'est autre qu\'une intelligence artificelle supérieure qui a pris les traits d\'un jeune enfant..."
        ,"Yuna_Madeleine"
        ,"Science-Fiction,Action,Thriller"
        ,"yF8aULSJtD0");
    }

    public void ajout131(){
        ajouter("Hugo Cabret"
        ,"2011-11-22"
        ,"Dans le Paris des années 30, le jeune Hugo est un orphelin de 12 ans qui vit dans une gare. Son passé est un mystère et son destin une énigme. De son père, il ne lui reste qu’un étrange automate dont il cherche la clé - en forme de cœur - qui pourrait le faire fonctionner. En rencontrant Isabelle, il a peut-être trouvé la clé, mais ce n’est que le début de l’aventure…"
        ,"Butterfield_Asa"
        ,"Aventure,Drame,Familial"
        ,"7qBJ6yL_QMc");
    }

    public void ajout132(){
        ajouter("Le Garçon au Pyjama rayé"
        ,"2008-05-07"
        ,"Seconde Guerre mondiale. Bruno a tout juste 9 ans lorsque son père, un officier nazi remarqué par le Führer, se voit confier le commandement du camp de concentration polonais d\'Auschwitz. Le petit garçon n\'apprécie guère de devoir quitter la belle et grande maison de Berlin pour se retrouver dans une demeure isolée et triste. De sa chambre, il aperçoit des hommes, des femmes et des enfants tous vêtus de pyjamas rayés. Personne ne lui explique qui ils sont, mais l\'innocence aidant, il va se lier d\'amitié avec un enfant juif..."
        ,"Butterfield_Asa"
        ,"Drame,Guerre,Histoire"
        ,"f7gmjW_CkE4");
    }


    public void ajout136(){
        ajouter("Forrest Gump"
        ,"1994-06-23"
        ,"Durant trois décennies particulièrement agitées, Forrest Gump vit une série d\'aventures le propulsant de l\'état de handicapé physique à celui de star du football, de héros du Vietnam au roi de la crevette, des honneurs de la Maison Blanche au bonheur d\'une grande histoire d\'amour. Forrest est le symbole d\'une époque, un candide dans une Amérique qui a perdu son innocence. Son cœur dépasse les limites de son QI…"
        ,"Hanks_Tom"
        ,"Comédie,Drame,Romance"
        ,"7pDDuroFBGM");
    }

    public void ajout137(){
        ajouter("Toy Story"
        ,"1995-10-30"
        ,"Dans un monde où les jouets vivent leur vie quand les humains ne sont pas présents, Toy Story emmène les spectateurs dans un voyage fantastique vu principalement par les yeux de deux rivaux : Woody, un cow-boy, et Buzz l\'Éclair, un ranger de l’espace. Ce duo va devoir apprendre à mettre ses différences de côté et s\'allier, lorsqu’il sera séparé de son propriétaire Andy."
        ,"Hanks_Tom"
        ,"Animation,Aventure,Familial,Comédie"
        ,"YzuUSdxoGUU");
    }

    public void ajout138(){
        ajouter("La Ligne verte"
        ,"1999-12-10"
        ,"Paul Edgecomb, pensionnaire centenaire d’une maison de retraite, est hanté par ses souvenirs. Gardien-chef du pénitencier de Cold Mountain en 1935, il était chargé de veiller au bon déroulement des exécutions des peines capitales, en s’efforçant d’adoucir les derniers moments des condamnés. Parmi eux, se trouvait un colosse du nom de John Coffey, accusé du viol et du meurtre de deux fillettes. Intrigué par cet homme candide et timide, aux dons magiques, Edgecomb va tisser avec lui des liens très forts."
        ,"Hanks_Tom"
        ,"Fantastique,Drame,Crime"
        ,"mccs8Ql8m8o");
    }

    public void ajout139(){
        ajouter("Birdman"
        ,"2014-10-17"
        ,"À l’époque où il incarnait un célèbre super‐héros, Riggan Thomson était mondialement connu. Mais de cette célébrité il ne reste plus grand‐chose, et il tente aujourd’hui de monter une pièce de théâtre à Broadway dans l’espoir de renouer avec sa gloire perdue. Durant les quelques jours qui précèdent la première, il va devoir tout affronter: sa famille et ses proches, son passé, ses rêves et son ego… S’il s’en sort, le rideau a une chance de s’ouvrir…"
        ,"Keaton_Michael"
        ,"Drame,Comédie"
        ,"jU-02YmPpMs");
    }

    public void ajout140(){
        ajouter("Batman"
        ,"1989-06-21"
        ,"Violence et corruption règnent dans la ville de Gotham City. La mafia dirige la ville à sa manière, au nez et à la barbe des autorités, complètement dépassées par les événements. Depuis quelques semaines cependant, un mystérieux justicier, drapé dans un costume de chauve-souris, terrorise les malfaiteurs et sème l\'inquiétude dans les rangs de la mafia. Une jeune journaliste, Vicki Vale, mène l\'enquête. C\'est ainsi qu\'elle fait la connaissance d\'un séduisant mais excentrique milliardaire, Bruce Wayne. Celui-ci n\'est autre que Batman, qui l\'a déjà sauvée. Elle rencontre également un odieux truand, Jack Napier, bras droit du parrain Carl Grissom..."
        ,"Keaton_Michael"
        ,"Fantastique,Action,Crime"
        ,"hGQo1axtj60");
    }

    public void ajout141(){
        ajouter("Thor : Ragnarok"
        ,"2017-10-02"
        ,"Thor est emprisonné de l’autre côté de l’univers sans son puissant marteau et se retrouve engagé dans une course contre le temps pour rejoindre Asgard et arrêter Ragnarok – la destruction de son monde natal et la fin de la civilisation Asgardienne – des mains d’une toute nouvelle menace, l’impitoyable Hela. Mais d’abord, il doit survivre à une compétition meurtrière de gladiateurs qui l’oppose à son ancien allié et compagnon Avenger – l’Incroyable Hulk !"
        ,"Brown_Clancy,Hemsworth_Chris"
        ,"Action,Aventure,Science-Fiction"
        ,"RtG6scyBIeM");
    }

    public void ajout142(){
        ajouter("Bob l\'éponge - Le film"
        ,"2004-11-14"
        ,"À Bikini Bottom, quelqu\'un a volé la couronne du roi Neptune, et le patron de Bob l\'éponge, M. Krabs, figure en tête des suspects. Convaincus de son innocence, Bob et Patrick partent pour Shell City avec l\'intention de le disculper et de restituer sa couronne à Neptune."
        ,"Brown_Clancy"
        ,"Familial,Animation,Comédie,Fantastique,Aventure"
        ,"2njtlJffhPo");
    }

    public void ajout143(){
        ajouter("Drive"
        ,"2011-09-15"
        ,"Un jeune homme solitaire, «The Driver», conduit le jour à Hollywood pour le cinéma en tant que cascadeur et la nuit pour des truands. Ultra professionnel et peu bavard, il a son propre code de conduite. Jamais il n’a pris part aux crimes de ses employeurs autrement qu’en conduisant et au volant, il est le meilleur! Shannon, le manager qui lui décroche tous ses contrats, propose à Bernie Rose, un malfrat notoire, d’investir dans un véhicule pour que son poulain puisse affronter les circuits de stock‐car professionnels. Celui‐ci accepte mais impose son associé, Nino, dans le projet. C’est alors que la route du pilote croise celle d’Irene et de son jeune fils. Pour la première fois de sa vie, il n’est plus seul. Lorsque le mari d’Irene sort de prison et se retrouve enrôlé de force dans un braquage pour s’acquitter d’une dette, il décide pourtant de lui venir en aide. L’expédition tourne mal…"
        ,"Gosling_Ryan"
        ,"Drame,Thriller,Crime"
        ,"4Wz990aqSDA");
    }

    public void ajout144(){
        ajouter("Matrix"
        ,"1999-03-30"
        ,"Programmeur anonyme dans un service administratif le jour, Thomas Anderson devient Neo la nuit venue. Sous ce pseudonyme, il est l’un des pirates les plus recherchés du cyber‐espace. À cheval entre deux mondes, Neo est assailli par d’étranges songes et des messages cryptés provenant d’un certain Morpheus. Celui‐ci l’exhorte à aller au‐delà des apparences et à trouver la réponse à la question qui hante constamment ses pensées : qu’est‐ce que la Matrice ? Nul ne le sait, et aucun homme n’est encore parvenu à en percer les défenses. Mais Morpheus est persuadé que Neo est l’Élu, le libérateur mythique de l’humanité annoncé selon la prophétie. Ensemble, ils se lancent dans une lutte sans retour contre la Matrice et ses terribles agents…"
        ,"Reeves_Keanu"
        ,"Action,Science-Fiction"
        ,"8xx91zoASLY");
    }

    public void ajout145(){
        ajouter("John Wick"
        ,"2014-10-22"
        ,"Depuis la mort de sa femme bien‐aimée, John Wick passe ses journées à retaper sa Ford Mustang de 1969, avec pour seule compagnie sa chienne Daisy. Il mène une vie sans histoire, jusqu’à ce qu’un malfrat sadique nommé Iosef Tarasof remarque sa voiture. John refuse de la lui vendre. Iosef n’acceptant pas qu’on lui résiste, s’introduit chez John avec deux complices pour voler la Mustang, et tuer sauvagement Daisy… John remonte la piste de Iosef jusqu’à New York. Un ancien contact, Aurelio, lui apprend que le malfrat est le fils unique d’un grand patron de la pègre, Viggo Tarasof. La rumeur se répand rapidement dans le milieu : le légendaire tueur cherche Iosef. Viggo met à prix la tête de John : quiconque l’abattra touchera une énorme récompense. John a désormais tous les assassins de New York aux trousses."
        ,"Reeves_Keanu,McShane_Ian"
        ,"Action,Thriller"
        ,"pWK5crMuhHY");
    }

    public void ajout146(){
        ajouter("John Wick 2"
        ,"2017-02-08"
        ,"John Wick est forcé de sortir de sa retraite volontaire par un de ses ex-associés qui cherche à prendre le contrôle d’une mystérieuse confrérie de tueurs internationaux. Parce qu’il est lié à cet homme par un serment, John se rend à Rome, où il va devoir affronter certains des tueurs les plus dangereux du monde."
        ,"Reeves_Keanu,McShane_Ian"
        ,"Action,Thriller,Crime"
        ,"d0u1o8vdxE8");
    }

    public void ajout147(){
        ajouter("Eternal Sunshine of the Spotless Mind"
        ,"2004-03-19"
        ,"Joël et Clémentine ne voient plus que les mauvais côtés de leur tumultueuse histoire d’amour, au point que celle‐ci fait effacer de sa mémoire toute trace de cette relation. Effondré, Joël contacte l’inventeur du procédé Lacuna, le Dr. Mierzwiak, pour qu’il extirpe également de sa mémoire tout ce qui le rattachait à Clémentine. Deux techniciens, Stan et Patrick, s’installent à son domicile et se mettent à l’œuvre, en présence de la secrétaire, Mary. Les souvenirs commencent à défiler dans la tête de Joël, des plus récents aux plus anciens, et s’envolent un à un, à jamais. Mais en remontant le fil du temps, Joël redécouvre ce qu’il aimait depuis toujours en Clémentine – l’inaltérable magie d’un amour dont rien au monde ne devrait le priver. Luttant de toutes ses forces pour préserver ce trésor, il engage alors une bataille de la dernière chance contre Lacuna…"
        ,"Winslet_Kate"
        ,"Science-Fiction,Drame,Romance"
        ,"iY8B790zlR0");
    }

    public void ajout148(){
        ajouter("The Reader"
        ,"2008-12-10"
        ,"Allemagne de l\'Ouest, au lendemain de la Seconde Guerre mondiale. Un adolescent, Michael Berg, fait par hasard la connaissance de Hanna, une femme de 35 ans dont il devient l\'amant. Commence alors une liaison secrète et passionnelle. Pendant plusieurs mois, Michael rejoint Hanna chez elle tous les jours, et l\'un de leurs jeux consiste à ce qu\'il lui fasse la lecture."
        ,"Winslet_Kate"
        ,"Drame,Romance"
        ,"Xo8VcKvMdD0");
    }

    public void ajout149(){
        ajouter("Mad Max : Fury Road"
        ,"2015-05-13"
        ,"Hanté par un lourd passé, Mad Max estime que le meilleur moyen de survivre est de rester seul. Cependant, il se retrouve embarqué par une bande qui parcourt le désert à bord d’un véhicule militaire piloté par l’Impératrice Furiosa. Ils fuient la Citadelle où sévit le terrible Immortan Joe qui s’est fait voler un objet irremplaçable. Enragé, ce Seigneur de guerre envoie ses hommes pour traquer les rebelles impitoyablement…"
        ,"Theron_Charlize,Hardy_Tom"
        ,"Action,Aventure,Science-Fiction"
        ,"mtolAJbj44s");
    }

    public void ajout150(){
        ajouter("Atomic Blonde"
        ,"2017-07-26"
        ,"L\'agent Lorraine Broughton est une des meilleures espionne du Service de renseignement de Sa Majesté ; à la fois sensuelle et sauvage et prête à déployer toutes ses compétences pour rester en vie durant sa mission impossible. Envoyée seule à Berlin dans le but de livrer un dossier de la plus haute importance dans cette ville au climat instable, elle s\'associe avec David Percival, le chef de station local, et commence alors un jeu d’espions des plus meurtriers."
        ,"Theron_Charlize"
        ,"Action,Thriller"
        ,"ZMI3pcFuUmw");
    }

    public void ajout151(){
        ajouter("Hancock"
        ,"2008-07-01"
        ,"Il y a les héros, les super-héros et il y a… Hancock. Ses super pouvoirs lui ont souvent permis de sauver d’innombrables vies, mais les dégâts monstrueux qu’il fait au passage ont fini par le rendre impopulaire. Les habitants de Los Angeles n’en peuvent plus et se demandent ce qu’ils ont bien pu faire pour mériter un « héros » pareil. Hancock est une tête de mule irascible qui n’est pas du genre à se soucier de ce que pensent les gens… du moins jusqu’à ce qu’il sauve la vie de Ray Embrey, un spécialiste des relations publiques. Le super-héros le plus détesté au monde commence alors à réaliser qu’il n’est pas aussi insensible qu’il voudrait le faire croire…"
        ,"Theron_Charlize"
        ,"Fantastique,Action"
        ,"FmpOgGrwXdQ");
    }

    public void ajout152(){
        ajouter("Venom"
        ,"2018-09-28"
        ,"Des symbiotes débarquent sur la Terre, parmi eux, Venom, qui va s’allier avec Eddie Brock. De son côté, un puissant scientifique tente de faire évoluer l’humanité avec les symbiotes, le duo d’anti‐héros va devoir tout faire pour l’arrêter !"
        ,"Hardy_Tom"
        ,"Science-Fiction,Action"
        ,"Pw9ZvTicmIg");
    }

    public void ajout153(){
        ajouter("Venom: Let There Be Carnage"
        ,"2021-09-30"
        ,"Environ un an après avoir affronté Riot, Eddie Brock « cohabite » toujours avec le symbiote Venom. Alors qu’il tente de relancer sa carrière de journaliste d\'investigation, Eddie se rend en prison pour interviewer le tueur en série Cletus Kasady. Il ignore que ce dernier est lui aussi l\'hôte d\'un symbiote, Carnage."
        ,"Hardy_Tom"
        ,"Science-Fiction,Action,Aventure"
        ,"-FmWuCgJmxo");
    }

    public void ajout154(){
        ajouter("Suicide Squad"
        ,"2016-08-03"
        ,"C’est tellement jouissif d’être un salopard ! Face à une menace aussi énigmatique qu’invincible, l’agent secret Amanda Waller réunit une armada de crapules de la pire espèce. Armés jusqu’aux dents par le gouvernement, ces Super‐Méchants s’embarquent alors pour une mission‐suicide. Jusqu’au moment où ils comprennent qu’ils ont été sacrifiés. Vont‐ils accepter leur sort ou se rebeller ?"
        ,"Smith_Will"
        ,"Action,Aventure,Fantastique"
        ,"WsfDNPFfxYM");
    }

    public void ajout155(){
        ajouter("Je suis une légende"
        ,"2007-12-12"
        ,"Robert Neville était un savant de haut niveau et de réputation mondiale, mais il en aurait fallu plus pour stopper les ravages de cet incurable et terrifiant virus d\'origine humaine. Mystérieusement immunisé contre le mal, Neville est aujourd\'hui le dernier homme à hanter les ruines de New York. Peut-être le dernier homme sur Terre… Depuis trois ans, il diffuse chaque jour des messages radio dans le fol espoir de trouver d\'autres survivants. Nul n\'a encore répondu. Mais Neville n\'est pas seul. Des mutants, victimes de cette peste moderne - on les appelle les « Infectés » - rôdent dans les ténèbres, observant ses moindres gestes, guettent sa première erreur. Devenu l\'ultime espoir de l\'humanité, Neville se consacre tout entier à sa mission : venir à bout du virus, en annuler les terribles effets en se servant de son propre sang. Ses innombrables ennemis lui en laisseront-ils le temps ? Le compte à rebours touche à sa fin…"
        ,"Smith_Will"
        ,"Drame,Science-Fiction,Thriller"
        ,"yTNKbLhVAQA");
    }

    public void ajout156(){
        ajouter("I, Robot"
        ,"2004-07-15"
        ,"En 2035, les robots sont devenus de parfaits assistants pour les êtres humains. Le détective Del Spooner enquête sur le meurtre du docteur Alfred Lanning, un chercheur en robotique. Le principal suspect semble être un androïde nommé Sonny. Or, si l\'on s\'en réfère aux lois de la robotique, les robots ne sont pas dotés de la faculté de tuer..."
        ,"Smith_Will,Greenwood_Bruce"
        ,"Action,Science-Fiction"
        ,"PjECS38ZGWE");
    }

    public void ajout157(){
        ajouter("Ninja Turtles"
        ,"2014-08-07"
        ,"Quand une cheville ouvrière menace la ville de New York, un groupe de guerriers tortues mutés doit sortir de l\'ombre pour protéger leur maison."
        ,"Fox_Megan"
        ,"Science-Fiction,Action,Aventure,Comédie"
        ,"M2PIjWZyCEc");
    }

    public void ajout158(){
        ajouter("Transformers"
        ,"2007-06-27"
        ,"Une guerre sans merci oppose depuis des temps immémoriaux deux races de robots extraterrestres : les Autobots et les cruels Decepticons. Son enjeu : la maîtrise de l\'univers… Dans les premières années du 21ème siècle, le conflit s\'étend à la Terre, et le jeune Sam Witwicky devient, à son insu, l\'ultime espoir de l\'humanité. Semblable à des milliers d\'adolescents, Sam n\'a connu que les soucis de son âge : le lycée, les amis, les voitures, les filles… Entraîné avec sa nouvelle copine, Mikaela, au cœur d\'un mortel affrontement, il ne tardera pas à comprendre le sens de la devise de la famille Witwicky : \"Sans sacrifice, point de victoire !\""
        ,"Fox_Megan"
        ,"Aventure,Science-Fiction,Action"
        ,"BO9d1C9ZsCs");
    }

    public void ajout159(){
        ajouter("Transformers 2 : La Revanche"
        ,"2009-06-19"
        ,"Si Sam a fait ce qu\'il a pu pour tirer un trait sur le conflit qui a eu lieu à Mission City et revenir à ses préoccupations quotidiennes, la guerre entre les Autobots et les Decepticons, tout en étant classée secret défense, a entraîné plusieurs changements. Le secteur 7 a ainsi été dissout et son plus fidèle soldat, l\'agent Simmons, a été révoqué sans ménagement. Résultat : une nouvelle agence, NEST, a été mise en place..."
        ,"Fox_Megan"
        ,"Science-Fiction,Action,Aventure"
        ,"Q5qjZsvup3c");
    }

    public void ajout160(){
        ajouter("Miss Peregrine et les enfants particuliers"
        ,"2016-09-27"
        ,"À la mort de son grand-père, Jacob découvre l\'existence d\'un monde mystérieux. Plusieurs indices le mènent dans ce lieu magique : la Maison de Miss Peregrine pour Enfants Particuliers. Mais le mystère et le danger s\'amplifient quand il apprend à connaître les résidents, leurs étranges pouvoirs... et leurs puissants ennemis. Finalement, Jacob découvre que seule sa propre « particularité » peut sauver ses nouveaux amis."
        ,"Green_Eva"
        ,"Fantastique,Aventure"
        ,"i-TiNC3zpPw");
    }

    public void ajout161(){
        ajouter("Casino Royale"
        ,"2006-11-14"
        ,"Pour sa première mission, James Bond affronte le tout-puissant banquier privé du terrorisme international, Le Chiffre. Pour achever de le ruiner et démanteler le plus grand réseau criminel qui soit, Bond doit le battre lors d\'une partie de poker à haut risque au Casino Royale. La très belle Vesper, attachée au Trésor, l\'accompagne afin de veiller à ce que l\'agent 007 prenne soin de l\'argent du gouvernement britannique qui lui sert de mise, mais rien ne va se passer comme prévu. Alors que Bond et Vesper s\'efforcent d\'échapper aux tentatives d\'assassinat du Chiffre et de ses hommes, d\'autres sentiments surgissent entre eux, ce qui ne fera que les rendre plus vulnérables..."
        ,"Green_Eva,Mikkelsen_Mads"
        ,"Aventure,Action,Thriller"
        ,"Tl9_c6wZiZU");
    }

    public void ajout162(){
        ajouter("300 : La Naissance d’un Empire"
        ,"2014-03-05"
        ,"En l’an 490 avant J.C., les troupes athéniennes doivent contrer les attaques de l’empire perse. Une grande bataille se prépare. Non loin d’Athènes, à 42 km au nord, Marathon est l’un des derniers remparts protégeant la grande Athènes. Les Perses sont nombreux, beaucoup plus nombreux que les Athéniens, qui vont devoir faire appel aux Spartiates pour les aider."
        ,"Green_Eva"
        ,"Action,Drame,Guerre"
        ,"L_S3nNZVPBQ");
    }

    public void ajout163(){
        ajouter("Les Huit Salopards"
        ,"2015-12-25"
        ,"Après la Guerre de Sécession, huit voyageurs se retrouvent coincés dans un refuge au milieu des montagnes. Alors que la tempête s\'abat au-dessus du massif, ils réalisent qu\'ils n\'arriveront peut-être pas à rallier Red Rock comme prévu..."
        ,"L. Jackson_Samuel"
        ,"Drame,Mystère,Western"
        ,"cM0WxEv5mwg");
    }

    public void ajout164(){
        ajouter("Captain Marvel"
        ,"2019-03-06"
        ,"Captain Marvel raconte l’histoire de Carol Danvers qui va devenir l’une des super-héroïnes les plus puissantes de l’univers lorsque la Terre se révèle l’enjeu d’une guerre galactique entre deux races extraterrestres."
        ,"L. Jackson_Samuel,Grace_Mckenna"
        ,"Action,Aventure,Science-Fiction"
        ,"rndLWLmwgeA");
    }

    public void ajout165(){
        ajouter("The Lost Daughter"
        ,"2021-12-16"
        ,"Lors de vacances à la mer en solitaire, Leda est fascinée par une jeune mère et sa fille qu\'elle observe sur la plage. Bouleversée par leur relation fusionnelle (ainsi que par leur grande famille bruyante et intimidante), Leda est submergée par la terreur, la confusion et l\'intensité de ses souvenirs de maternité précoce. Un acte impulsif la replonge dans les méandres étranges et inquiétants de son esprit, l\'obligeant à affronter les choix peu conventionnels qui ont été les siens en tant que jeune mère, et leurs conséquences."
        ,"James_Ellie"
        ,"Drame"
        ,"xNq9YOfL0Zs");
    }

    public void ajout166(){
        ajouter("VS."
        ,"2018-10-19"
        ,"Un jeune homme tente de se faire une place au sein de la scène hostile et passionnante des battles de rap britanniques."
        ,"James_Ellie"
        ,"Drame,Musique"
        ,"Pvx9Yjt2F1c");
    }

    public void ajout167(){
        ajouter("La Main"
        ,"2023-07-26"
        ,"Lorsqu\'un groupe d\'amis découvre comment conjurer les esprits à l\'aide d\'une mystérieuse main hantée, ils deviennent accros à ce nouveau frisson, et l’expérience fait le tour des réseaux sociaux. Une seule règle à respecter : ils ne doivent pas tenir la main plus de 90 secondes. Lorsque l’un d’entre eux l’enfreint, ils vont être rattrapés par les esprits, les obligeant à choisir : à qui se fier, aux morts ou aux vivants ?"
        ,"Jensen_Alexandra"
        ,"Horreur,Thriller"
        ,"6_Z5AxmVfto");
    }

    public void ajout168(){
        ajouter("Twilight, chapitre 1 : Fascination"
        ,"2008-11-20"
        ,"Isabella Swan, 17 ans, déménage à Forks, petite ville pluvieuse dans l\'État de Washington, pour vivre avec son père. Elle s\'attend à ce que sa nouvelle vie soit aussi ennuyeuse que la ville elle-même. Or, au lycée, elle est terriblement intriguée par le comportement d\'une étrange fratrie, deux filles et trois garçons. Bella tombe follement amoureuse de l\'un d\'eux, Edward Cullen. Une relation sensuelle et dangereuse commence alors entre les deux jeunes gens : lorsque Isabella comprend que Edward est un vampire, il est déjà trop tard."
        ,"Stewart_Kristen,Pattinson_Robert"
        ,"Fantastique,Drame,Romance"
        ,"xzO_Iiwt4b4");
    }

    public void ajout169(){
        ajouter("Twilight, chapitre 2 : Tentation"
        ,"2009-11-18"
        ,"\"Tu ne me reverras plus. Je ne reviendrai pas. Poursuis ta vie, ce sera comme si je n\'avais jamais existé.\" Abandonnée par Edward, celui qu\'elle aime passionnément, Bella ne s\'en relève pas. Comment oublier son amour pour un vampire et revenir à une vie normale ? Pour combler son vide affectif, Bella court après le danger et prends des risques de plus en plus inconsidérés. Edward n\'étant plus là pour la protéger, c\'est Jacob, l\'ami discret et indéfectible qui va la défendre et veiller sur elle. Mais peu à peu elle réalise l\'ambigüité des sentiments qu\'ils éprouvent l\'un envers l\'autre..."
        ,"Stewart_Kristen"
        ,"Aventure,Fantastique,Drame,Romance"
        ,"ShHNEvFPtvc");
    }

    public void ajout170(){
        ajouter("Twilight, chapitre 4 : Révélation, 1re partie"
        ,"2011-11-16"
        ,"Bella et Edward Cullen sont unis par les liens du mariage et partent en lune de miel sur l’île d’Esmée. Le mariage consommé, Bella découvre qu’elle est enceinte. Décidée à rester humaine jusqu’à l’accouchement, la vie de Bella est mise en péril par son bébé, mi-humain mi-vampire. De retour à Forks, le couple doit faire face à cette grossesse hors norme et à l’hostilité qu’elle suscite : les Quilleutes sont sur le pied de guerre, prêts à affronter les Cullen et à tuer ce qu’ils considèrent comme une aberration. Alors que la trêve entre les vampires et les loups est menacée, Edward et Bella sont de nouveau confrontés à un choix…"
        ,"Stewart_Kristen"
        ,"Aventure,Fantastique,Romance"
        ,"_jWriOZlsnw");
    }

    public void ajout172(){
        ajouter("Jack Reacher"
        ,"2012-12-20"
        ,"Lentement, méticuleusement, un sniper armé d’un fusil à lunettes repère ses victimes au sein de la foule affairée d’une grande ville. Il tire six fois, abat cinq personnes. Arrêté, interrogé, apparemment clairement responsable du carnage, l’homme n’avoue pas. Il ne prononce qu’une seule phrase lors de son interrogatoire: «Trouvez Jack Reacher». Et Reacher, justement, est perturbé par cette affaire dont la résolution lui semble trop simple. Il connaît le tireur, un militaire surentraîné, et sait que jamais, il n’aurait raté une de ses cibles. Avec l’aide d’une avocate, Helen Rodin, Reacher va mettre le nez dans un imbroglio aux allures de complot explosif…"
        ,"Pike_Rosamund"
        ,"Crime,Drame,Thriller,Action"
        ,"ZGFBa7Exr18");
    }

    public void ajout173(){
        ajouter("I Care a Lot."
        ,"2021-02-19"
        ,"Marla Grayson est une tutrice réputée spécialisée auprès d\'individus âgés et riches. Aux dépens de ces derniers, elle mène une vie de luxe. Mais sa prochaine victime s\'avère avoir de dangereux secrets. Marla va devoir utiliser son esprit et sa ruse si elle souhaite rester en vie..."
        ,"Pike_Rosamund"
        ,"Comédie,Crime,Thriller,Drame"
        ,"9VX-oPAAB_4");
    }

    public void ajout177(){
        ajouter("Star Trek"
        ,"2009-05-06"
        ,"Deux officiers que tout oppose, James T. Kirk, arrogant et rebelle, et le jeune Spock, à moitié Humain et à moitié Vulcain, embarquent à bord de l\'USS Enterprise, le vaisseau spatial le plus sophistiqué jamais construit. Face à eux, surgi du futur, Nero, un Rémien assoiffé de vengeance qui menace la Fédération tout entière. Kirk et Spock, dont les relations sont pour le moins compliquées, vont devoir s\'allier pour empêcher l\'inévitable..."
        ,"Greenwood_Bruce"
        ,"Science-Fiction,Action,Aventure"
        ,"h33U-i_gXTo");
    }

    public void ajout178(){
        ajouter("Kick-Ass"
        ,"2010-03-22"
        ,"Dave Lizewski est un adolescent gavé de comics qui ne vit que pour ce monde de super‐héros et d’incroyables aventures. Décidé à vivre son obsession jusque dans la réalité, il se choisit un nom – Kick‐Ass – se fabrique lui‐même un costume, et se lance dans une bataille effrénée contre le crime. Dans son délire, il n’a qu’un seul problème : Kick‐Ass n’a pas le moindre superpouvoir… Le voilà pourchassé par toutes les brutes de la ville. Mais Kick‐Ass s’associe bientôt à d’autres délirants copycats décidés eux aussi à faire régner la justice. Parmi eux, une enfant de 11 ans, Hit Girl et son père Big Daddy, mais aussi Red Mist. Le parrain de la mafia locale, Frank D’Amico, va leur donner l’occasion de montrer ce dont ils sont capables…"
        ,"Grace_Chloë"
        ,"Action,Crime"
        ,"__3BUMBPSus");
    }

    public void ajout179(){
        ajouter("La 5ème Vague"
        ,"2016-01-14"
        ,"Quatre vagues d’attaques, chacune plus mortelle que la précédente, ont décimé la presque totalité de la Terre. Terrifiée, se méfiant de tout, Cassie Sullivan est en fuite et tente désespérément de sauver son jeune frère. Alors qu’elle se prépare à affronter la cinquième vague, aussi inévitable que fatale, elle va faire équipe avec un jeune homme qui pourrait bien représenter son dernier espoir – si toutefois elle peut lui faire confiance…"
        ,"Grace_Chloë"
        ,"Science-Fiction,Aventure,Action"
        ,"l3ImzAoQq7k");
    }

    public void ajout180(){
        ajouter("Carrie, la vengeance"
        ,"2013-10-16"
        ,"Timide et surprotégée par sa mère très pieuse, Carrie est une lycéenne rejetée par ses camarades. Le soir du bal de fin d’année, elle subit une sale blague de trop. Carrie déchaîne alors de terrifiants pouvoirs surnaturels auxquels personne n’échappera..."
        ,"Grace_Chloë"
        ,"Drame,Horreur"
        ,"sYjn50LDTQs");
    }

    public void ajout182(){
        ajouter("Demain c\'est aujourd\'hui"
        ,"2022-11-29"
        ,"Été 1991. La famille Gaspar est en vacances. Suite à une dispute avec son père, Lulu décide de fuir en compagnie de son petit ami. Alors que les membres de la famille font du pédalo, un orage violent éclate. En regagnant la rive, ils se rendent compte qu\'ils ont voyagé dans le temps et qu\'ils sont en 2022. Pourront-ils retrouver les années 90 ?"
        ,"Guevara_Gabriel"
        ,"Comédie"
        ,"IcpCkXBnp2s");
    }

    public void ajout183(){
        ajouter("Charter"
        ,"2020-03-13"
        ,""
        ,"Guevara_Gabriel"
        ,"Drame"
        ,"yEjCANdSQ9I");
    }

    public void ajout184(){
        ajouter("Pas un bruit"
        ,"2016-03-12"
        ,"Une écrivaine sourde qui s’est retirée au fond des bois pour vivre dans la solitude doit défendre sa vie en silence quand un tueur masqué apparaît à sa fenêtre."
        ,"Siegel_Kate"
        ,"Horreur,Thriller"
        ,"7V0p7WZIK6s");
    }

    public void ajout185(){
        ajouter("Scream"
        ,"2022-01-12"
        ,"Vingt-cinq ans après que la paisible ville de Woodsboro a été frappée par une série de meurtres violents, un nouveau tueur revêt le masque de Ghostface et prend pour cible un groupe d’adolescents. Il est déterminé à faire ressurgir les sombres secrets du passé."
        ,"Ortega_Jenna"
        ,"Horreur,Mystère,Thriller"
        ,"z_FVI4_L1gg");
    }


    public void ajout189(){
        ajouter("Sans un bruit"
        ,"2018-04-03"
        ,"Une famille tente de survivre sous la menace de mystérieuses créatures qui attaquent au moindre bruit. S’ils vous entendent, il est déjà trop tard."
        ,"Blunt_Emily"
        ,"Horreur,Drame,Science-Fiction"
        ,"GzFz9Y6Zkos");
    }

    public void ajout190(){
        ajouter("Sicario"
        ,"2015-09-17"
        ,"La zone frontalière entre les États‐Unis et le Mexique est devenue un territoire de non‐droit. Kate, une jeune recrue idéaliste du FBI, y est enrôlée pour aider un groupe d’intervention d’élite dirigé par un agent du gouvernement dans la lutte contre le trafic de drogues. Menée par un consultant énigmatique l’équipe se lance dans un périple clandestin, obligeant Kate à remettre en question ses convictions pour pouvoir survivre."
        ,"Blunt_Emily,del Toro_Benicio"
        ,"Action,Crime,Thriller"
        ,"Y6Y1IkKVmOM");
    }

    public void ajout191(){
        ajouter("Edge of Tomorrow"
        ,"2014-05-27"
        ,"Dans un futur proche, des hordes d’extraterrestres ont livré une bataille acharnée contre la Terre et semblent désormais invincibles : aucune armée au monde n’a réussi à les vaincre. Le commandant William Cage, qui n’a jamais combattu de sa vie, est envoyé, sans la moindre explication, dans ce qui ressemble à une mission‐suicide. Il meurt en l’espace de quelques minutes et se retrouve projeté dans une boucle temporelle, condamné à revivre le même combat et à mourir de nouveau indéfiniment…"
        ,"Blunt_Emily,Cruise_Tom"
        ,"Action,Science-Fiction"
        ,"NYtObD3aAZs");
    }

    public void ajout192(){
        ajouter("The Rocky Horror Picture Show"
        ,"1975-08-14"
        ,"Une nuit d\'orage, la voiture de Janet et Brad, un couple coincé qui vient de se fiancer, tombe en panne. Obligés de se réfugier dans un mystérieux château, ils vont faire la rencontre de ses occupants pour le moins bizarres, qui se livrent à de bien étranges expériences."
        ,"Curry_Tim"
        ,"Comédie,Science-Fiction,Fantastique,Horreur"
        ,"67W7ZVlqF10");
    }

    public void ajout193(){
        ajouter("Cluedo"
        ,"1985-12-13"
        ,"L\'adaptation du célèbre jeu de société Cluedo. Six personnes sont invitées à passer une soirée dans un mystérieux manoir. Un meurtre est commis et chacun a quelque chose à se reprocher, alors qui est le coupable ?"
        ,"Curry_Tim"
        ,"Comédie,Thriller,Crime,Mystère"
        ,"KEXdWfsKZ1k");
    }

    public void ajout194(){
        ajouter("Charlie et ses drôles de dames"
        ,"2000-11-02"
        ,"Eric Knox a conçu un logiciel révolutionnaire qui, s’il tombait en de mauvaises mains, mettrait en danger la vie privée de tous ceux qui approchent un ordinateur. Lorsqu’il est enlevé, la présidente de Knox Technologies fait appel à Charlie et à ses trois jeunes détectives aussi sexy qu’intelligentes. Natalie, Dylan et Alex s’intéressent d’abord à Roger Corwin, rival de Knox et propriétaire du plus grand réseau de télécommunications par satellite du monde. Pour infiltrer le cercle de ses proches, les trois jeunes femmes ne reculent devant rien."
        ,"Curry_Tim"
        ,"Action,Aventure,Comédie,Crime,Thriller"
        ,"qrcTDuCS55g");
    }

    public void ajout196(){
        ajouter("Once Upon a Time… in Hollywood"
        ,"2019-07-24"
        ,"En 1969, Rick Dalton – star déclinante d\'une série télévisée de western – et Cliff Booth – sa doublure de toujours – assistent à la métamorphose artistique d\'un Hollywood qu\'ils ne reconnaissent plus du tout en essayant de relancer leurs carrières."
        ,"Sweeney_Sydney"
        ,"Comédie,Drame,Thriller"
        ,"rCKjRdjjjWM");
    }

    public void ajout197(){
        ajouter("Ninja II Ultime Violence"
        ,"1983-09-07"
        ,"Après que sa famille est tué au Japon par des ninjas, Cho et son fils Kane viennent en Amérique pour commencer une nouvelle vie. Il ouvre un magasin de poupée, mais importe sans le savoir l\'héroïne dans les poupées. Quand il découvre que son ami a trahi lui, Cho doit se préparer à la bataille finale, il n\'a jamais été impliqué."
        ,"Roberts_Arthur"
        ,"Action,Crime"
        ,"9bqSpOTddeQ");
    }

    public void ajout198(){
        ajouter("Faut Trouver Le Joint"
        ,"1978-05-16"
        ,"Après avoir commencé leur carrière comme musiciens de rock, Cheech Marin et Tommy Chong, véritables stars aux Etats-Unis, font leur début au cinéma dans une “Rock and Roll comedy” complètement folle. Le délire des deux comiques en pétard va les mener tout droit au Théâtre Roxy de Los Angeles où Cheech, en tutu rose et Chong, enrobé dans une cape bleu, vont participer à un concert extrêmement mouvementé... Une scène finale d’anthologie ! Pour tous les consommateurs de comédies déjantées et totalement allumées ! Attention : être trop sérieux est nuisible à la santé !"
        ,"Roberts_Arthur"
        ,"Comédie"
        ,"sLfIoIjy3So");
    }

    public void ajout199(){
        ajouter("Le vampire de l\'espace"
        ,"1988-05-20"
        ,"Lorsqu\'un mystérieux étranger arrive en ville, il embauche immédiatement l\'infirmière Nadine Story, lui offrant un généreux salaire. Ses soupçons sont toutefois éveillés lorsque les transfusions sanguines de son patient deviennent de plus en plus fréquentes. Après avoir mené sa petite enquête, elle découvre que son patron est en réalité un vampire de l\'espace… et la chasse commence !"
        ,"Roberts_Arthur"
        ,"Horreur,Science-Fiction"
        ,"mCBxum0WWHA");
    }

    public void ajout200(){
        ajouter("Casper"
        ,"1995-05-26"
        ,"Un tout jeune fantôme, Casper, ronge son frein et s\'ennuie à mourir en compagnie de ses oncles dans le manoir de Whipstaff. Quand une petite fille et son père viennent s\'installer dans la maison hantée, il pense pouvoir enfin s\'amuser..."
        ,"Ricci_Christina,Pearson_Malachi"
        ,"Fantastique,Comédie,Familial"
        ,"jVCe_CtlKwc");
    }

    public void ajout201(){
        ajouter("Sleepy hollow, la légende du cavalier sans tête"
        ,"1999-11-19"
        ,"En 1799, dans une bourgade de la Nouvelle-Angleterre, plusieurs cadavres sont successivement retrouvés décapités. Les têtes ont disparu. Terrifiés, les habitants sont persuadés que ces meurtres sont commis par un étrange et furieux cavalier, dont la rumeur prétend qu\'il est lui-même sans tête. Les autorités new-yorkaises envoient alors leur plus fin limier pour éclaircir ce mystère. Ichabod Crane ne croit ni aux légendes, ni aux vengeances post-mortem. Mais, à peine arrivé, il succombe au charme étrange et vénéneux de la belle Katrina Van Tassel."
        ,"Ricci_Christina"
        ,"Drame,Fantastique,Thriller,Mystère,Horreur"
        ,"-P0aLlEgXj8");
    }

    public void ajout202(){
        ajouter("Pénélope"
        ,"2006-03-01"
        ,"Une sorcière a jeté un sort sur la première fille qui nait dans la famille Wilhern : Pénélope. Pour y échapper, elle devra épouser un garçon issu de la noblesse. Pénélope est une romantique. Elle décide de fuir loin de sa famille et d\'affronter le Monde. Elle découvrira que le mauvais sort, il faut l\'ignorer et s\'accepter telle qu\'elle est."
        ,"Ricci_Christina"
        ,"Fantastique,Comédie,Romance"
        ,"H1sk-QO2VZU");
    }

    public void ajout203(){
        ajouter("Warcraft : Le Commencement"
        ,"2016-05-25"
        ,"Le pacifique royaume d\'Azeroth est au bord de la guerre alors que sa civilisation doit faire face à une redoutable race d’envahisseurs: des guerriers Orcs fuyant leur monde moribond pour en coloniser un autre. Alors qu’un portail s’ouvre pour connecter les deux mondes, une armée fait face à la destruction et l\'autre à l\'extinction. De côtés opposés, deux héros vont s’affronter et décider du sort de leur famille, de leur peuple et de leur patrie."
        ,"Fimmel_Travis"
        ,"Action,Aventure,Fantastique"
        ,"d7THxrD72eE");
    }

    public void ajout204(){
        ajouter("Danger Close : The Battle of Long Tan"
        ,"2019-08-08"
        ,"Pendant trois heures et demie, sous la pluie battante, dans la boue et les arbres brisés d\'une plantation d\'hévéas appelée Long Tan, le major Harry Smith et sa compagnie dispersée de 108 soldats australiens et néo-zélandais, jeunes et pour la plupart inexpérimentés, se battent pour leur vie, retenant une force ennemie écrasante de 2 500 soldats endurcis par la guerre, les Vietnamiens et les Nord-Vietnamiens. Leurs munitions étant épuisées, leurs pertes s\'accumulant et l\'ennemi rassemblé pour un assaut final, chaque homme commence à chercher la force de vaincre un avenir incertain avec honneur, décence et courage."
        ,"Fimmel_Travis"
        ,"Guerre,Action,Drame,Histoire"
        ,"_E0J11-rB7Q");
    }

    public void ajout205(){
        ajouter("Barbie"
        ,"2023-07-19"
        ,"Parallèlement au monde réel, il existe Barbie Land, un monde parfait où les poupées Barbie vivent joyeusement, persuadées d\'avoir rendu les filles humaines heureuses. Mais un jour, une Barbie commence à se poser des questions et à devenir humaine. Sur les conseils d\'une Barbie étrange, elle part pour le monde réel afin de retrouver la fille à laquelle elle appartenait afin de pouvoir retrouver sa perfection. Dans sa quête, elle est accompagnée par un Ken fou amoureux d\'elle qui va également trouver un sens à sa vie dans le monde réel..."
        ,"Greenblatt_Ariana,Mackey_Emma"
        ,"Comédie,Aventure,Fantastique"
        ,"2oOzWcbVf1c");
    }

    public void ajout206(){
        ajouter("65 : La Terre d\'avant"
        ,"2023-03-02"
        ,"Après un terrible crash sur une planète inconnue, le pilote Mills découvre rapidement qu’il a en réalité échoué sur Terre… il y a 65 millions d’années. Pour réussir leur unique chance de sauvetage, Mills et Koa, l’unique autre survivante du crash, doivent se frayer un chemin à travers des terres inconnues peuplées de dangereuses créatures préhistoriques dans un combat épique pour leur survie."
        ,"Greenblatt_Ariana"
        ,"Science-Fiction,Action,Aventure"
        ,"RF4pH6Xsorc");
    }

    public void ajout207(){
        ajouter("Love and Monsters"
        ,"2020-10-16"
        ,"Un jeune homme tente de survivre dans un monde post-apocalyptique, envahi par des monstres. Un expert lui enseigne la manière de les combattre..."
        ,"Greenblatt_Ariana"
        ,"Comédie,Action,Aventure,Fantastique"
        ,"9yxaR9qwbwI");
    }

    public void ajout209(){
        ajouter("Bula"
        ,"2023-09-26"
        ,""
        ,"Guinto_Robb"
        ,"Thriller,Horreur"
        ,"aqAuH-Myke8");
    }

    public void ajout211(){
        ajouter("Hôtel Transylvanie"
        ,"2012-09-20"
        ,"Bienvenue à l’Hôtel Transylvanie, le somptueux hôtel de Dracula, où les monstres et leurs familles peuvent enfin vivre leur vie, se détendre et faire « monstrueusement » la fête comme ils en ont envie sans être embêtés par les humains. Pour l’anniversaire de sa fille, la jeune Mavis, qui fête ses 118 printemps, Dracula invite les plus célèbres monstres du monde – Frankenstein et sa femme, la Momie, l’Homme Invisible, une famille de loups‐garous, et bien d’autres encore… Tout se passe très bien, jusqu’à ce qu’un humain débarque par hasard à l’hôtel et se lie d’amitié avec Mavis…"
        ,"Sandler_Adam"
        ,"Animation,Comédie,Familial,Fantastique"
        ,"ySZBIoNosz0");
    }

    public void ajout212(){
        ajouter("Pixels"
        ,"2015-07-16"
        ,"À l’époque de leur jeunesse, dans les années 80, Sam Brenner, Will Cooper, Ludlow Lamonsoff et Eddie « Fire Blaster » Plant ont sauvé le monde des milliers de fois… en jouant à des jeux d’arcade à 25 cents la partie. Mais aujourd’hui, ils vont devoir le faire pour de vrai… Lorsque des aliens découvrent des vidéos d’anciens jeux et les prennent pour une déclaration de guerre, ils lancent l’assaut contre la Terre. Ces mêmes jeux d’arcade leur servent de modèles pour leurs attaques. Cooper, qui est désormais Président des États‐Unis, fait alors appel à ses vieux potes pour empêcher la destruction de la planète par PAC‐MAN, Donkey Kong, Galaga, Centipede et les Space Invaders… Les gamers pourront compter sur l’aide du lieutenant‐colonel Violet Van Patten, une spécialiste qui va leur fournir des armes uniques…"
        ,"Sandler_Adam"
        ,"Action,Comédie,Science-Fiction"
        ,"-0PAYvvZy3w");
    }

    public void ajout213(){
        ajouter("Amour et Amnésie"
        ,"2004-02-13"
        ,"La vie de Henry Roth est simple. Lorsque ce vétérinaire spécialiste des animaux marins polaires ne travaille pas au parc Sea Life de Hawaï, il se consacre à son autre passion : les aventures sans lendemain avec de jolies touristes en mal de romantisme. Henry refuse toute liaison sérieuse par peur de compromettre son rêve de partir en Alaska étudier les morses… Pourtant, lorsqu’il rencontre Lucy, il est tout de suite fasciné. Enfreignant sa propre règle qui lui interdit de charmer des jeunes femmes du coin, Henry aborde Lucy, discute agréablement avec elle et obtient même un rendez‐vous pour le lendemain. Lorsqu’il s’y présente, Lucy ne le reconnaît pas et hurle à l’agression. Henry va découvrir que la jeune femme souffre d’une étrange maladie qui, chaque nuit, lui fait tout oublier. S’il veut vivre quelque chose avec elle, chaque jour devra être comme le premier…"
        ,"Sandler_Adam"
        ,"Comédie,Romance"
        ,"1zl_w-BLCNQ");
    }

    public void ajout214(){
        ajouter("Time Out"
        ,"2011-10-27"
        ,"Bienvenue dans un monde où le temps a remplacé l\'argent. Génétiquement modifiés, les Hommes ne vieillissent plus après 25 ans. Mais à partir de cet âge, il faut \"gagner\" du temps pour rester en vie. Alors que les riches, jeunes et beaux pour l’éternité, accumulent le temps par dizaines d\'années, les autres mendient, volent et empruntent les quelques heures qui leur permettront d\'échapper à la mort. Un homme, accusé à tort de meurtre, prend la fuite avec une otage qui deviendra son alliée. Plus que jamais, chaque minute compte."
        ,"Seyfried_Amanda"
        ,"Action,Thriller,Science-Fiction"
        ,"bVZsx_a488w");
    }

    public void ajout215(){
        ajouter("Cher John"
        ,"2010-02-04"
        ,"Lorsque John Tyree, un soldat des Forces Spéciales en permission, et Savannah Curtis, une étudiante idéaliste, se rencontrent sur une plage, c\'est le coup de foudre. Bien qu\'appartenant à deux mondes différents, une passion absolue les réunit pendant deux semaines. John repart ensuite en mission et Savannah retourne à l\'université, mais ils promettent de s\'écrire et à travers leurs lettres enflammées, leur amour ne fait que grandir. Chaque jour plus inquiète pour la sécurité de son bien-aimé, Savannah s\'interroge. Alors que désirs et responsabilités s\'opposent toujours plus, le couple lutte pour maintenir ses engagements. Quand une tragédie oblige John à rentrer, les deux jeunes gens se retrouvent face à leurs contradictions. John et Savannah vont découvrir si leur amour peut vraiment survivre à tout..."
        ,"Seyfried_Amanda"
        ,"Drame,Romance,Guerre"
        ,"THLibxt1juU");
    }

    public void ajout216(){
        ajouter("Le Chaperon rouge"
        ,"2011-03-10"
        ,"Dans une histoire inspirée d’un célèbre conte de fées, une adolescente se retrouve en grand danger quand son village décide de chasser les loups-garous qui terrorisent la population à chaque pleine lune. Dans un endroit où tout le monde a un secret et est suspect, notre héroïne doit apprendre à suivre son cœur et trouver en qui elle peut avoir confiance."
        ,"Seyfried_Amanda"
        ,"Action,Aventure,Horreur,Fantastique,Thriller"
        ,"dlbTw99xpRM");
    }

    public void ajout217(){
        ajouter("Mort sur le Nil"
        ,"2022-02-09"
        ,"Au cours d’une luxueuse croisière sur le Nil, Hercule Poirot voit ses vacances en Égypte se transformer en chasse au meurtrier lorsqu’une idyllique lune de miel est brutalement interrompue par une série de morts violentes. Sur fond de paysages grandioses mais dans une atmosphère inquiétante empreinte de dangers, cette sombre affaire d’amour obsessionnel aux conséquences meurtrières déstabilisera les certitudes de chacun, enchaînant rebondissements et retournements de situation jusqu’à l’incroyable dénouement !"
        ,"Mackey_Emma"
        ,"Mystère,Crime,Thriller"
        ,"SNwmk4-o8hI");
    }

    public void ajout218(){
        ajouter("Black Swan"
        ,"2010-12-03"
        ,"Rivalités dans la troupe du New York City Ballet. Nina est prête à tout pour obtenir le rôle principal du Lac des cygnes que dirige l’ambigu Thomas. Mais elle se trouve bientôt confrontée à la belle et sensuelle nouvelle recrue, Lily…"
        ,"Portman_Natalie,Kunis_Mila"
        ,"Drame,Thriller,Horreur"
        ,"BwfH0HA-7Sw");
    }

    public void ajout219(){
        ajouter("V pour Vendetta"
        ,"2006-02-23"
        ,"Londres, au 21ème siècle… Evey Hammond ne veut rien oublier de l’homme qui lui sauva la vie et lui permit de dominer ses peurs les plus lointaines. Mais il fut un temps où elle n’aspirait qu’à l’anonymat pour échapper à une police secrète omnipotente. Comme tous ses concitoyens, trop vite soumis, elle acceptait que son pays ait perdu son âme et se soit donné en masse au tyran Sutler et à ses partisans. Une nuit, alors que deux « gardiens de l’ordre » s’apprêtaient à la violer dans une rue déserte, Evey vit surgir son libérateur. Et rien ne fut plus comme avant. Son apprentissage commença quelques semaines plus tard sous la tutelle de « V ». Evey ne connaîtrait jamais son nom et son passé, ne verrait jamais son visage atrocement brûlé et défiguré, mais elle deviendrait à la fois son unique disciple, sa seule amie et le seul amour d’une vie sans amour…"
        ,"Portman_Natalie"
        ,"Action,Thriller,Science-Fiction"
        ,"hmbQgEHGpLg");
    }

    public void ajout220(){
        ajouter("Thor"
        ,"2011-04-21"
        ,"Thor, le héros du nouveau film issu de l\'univers Marvel, est un guerrier tout-puissant et arrogant dont les actes téméraires font renaître de nos jours un conflit ancestral. À cause de cela, il est banni du Royaume mythique d’Asgard et est condamné à vivre parmi les humains. Mais lorsque les forces du Mal d’Asgard s’apprêtent à envahir la Terre, Thor découvre enfin ce que signifie \"être un héros\"."
        ,"Portman_Natalie,Hemsworth_Chris"
        ,"Aventure,Fantastique,Action"
        ,"wPPim0we5m8");
    }

    public void ajout221(){
        ajouter("Polar"
        ,"2019-01-25"
        ,"Quand un assassin s’aperçoit qu’il est la cible d’un meurtrier, il reprend du service pour affronter une armée de tueurs plus jeunes et plus impitoyables que lui."
        ,"Winnick_Katheryn"
        ,"Action,Crime,Drame"
        ,"IMU28mWvRAc");
    }

    public void ajout222(){
        ajouter("La Tour sombre"
        ,"2017-08-03"
        ,"Le dernier Pistolero, Roland Deschain, est condamné à livrer une éternelle bataille contre Walter O’Dim, alias l’Homme en noir, qu’il doit à tout prix empêcher de détruire la Tour sombre, clé de voûte de la cohésion de l’univers. Le destin de tous les mondes est en jeu, le bien et le mal vont s’affronter dans l’ultime combat, car Roland est le seul à pouvoir défendre la Tour contre l’Homme en noir…"
        ,"Winnick_Katheryn"
        ,"Fantastique,Action,Science-Fiction"
        ,"2D3QJ1G2MRA");
    }

    public void ajout223(){
        ajouter("Run Hide Fight"
        ,"2021-06-17"
        ,"Zoe est sur le point d\'obtenir son diplôme à un moment très difficile de sa vie après la mort de sa mère. Pour se distraire, elle part à la chasse avec son père Todd, ancien membre des forces spéciales. Un jour, alors que Zoe est à l\'école, quatre élèves assiègent le bâtiment et pénètrent dans le bar de l\'école avec une camionnette. En utilisant les techniques qu\'elle a apprises de son père, Zoe parvient à échapper aux assaillants."
        ,"May_Isabel"
        ,"Action,Crime,Thriller"
        ,"2Kh3jccZocc");
    }

    public void ajout224(){
        ajouter("Expendables : Unité spéciale"
        ,"2010-08-03"
        ,"Ce ne sont ni des mercenaires, ni des agents secrets. Ils choisissent eux‐mêmes leurs missions et n’obéissent à aucun gouvernement. Ils ne le font ni pour l’argent, ni pour la gloire, mais parce qu’ils aident les cas désespérés. Depuis dix ans, Izzy Hands, de la CIA, est sur les traces du chef de ces hommes, Barney Ross. Parce qu’ils ne sont aux ordres de personne, il devient urgent de les empêcher d’agir. Éliminer un général sud‐américain n’est pas le genre de job que Barney Ross accepte, mais lorsqu’il découvre les atrocités commises sur des enfants, il ne peut refuser. Avec son équipe d’experts, Ross débarque sur l’île paradisiaque où sévit le tyran. Lorsque l’embuscade se referme sur eux, il comprend que dans son équipe, il y a un traître. Après avoir échappé de justesse à la mort, ils reviennent aux États‐Unis, où chaque membre de l’équipe est attendu. Il faudra que chacun atteigne les sommets de son art pour en sortir et démasquer celui qui a trahi…"
        ,"Stallone_Sylvester"
        ,"Thriller,Aventure,Action"
        ,"5RcnSc08lJQ");
    }

    public void ajout225(){
        ajouter("Rocky"
        ,"1976-11-21"
        ,"Dans les quartiers populaires de Philadelphie, Rocky Balboa collecte des dettes non payées pour Tony Gazzo, un usurier, et dispute de temps à autre, pour quelques dizaines de dollars, des combats de boxe sous l\'appellation de \'l\'étalon italien\'. Déçu de le voir gâcher son talent, son entraîneur Mickey le laisse tomber, tandis qu\'Apollo Creed, champion en titre, cherche un nouveau challenger..."
        ,"Stallone_Sylvester"
        ,"Drame,Romance"
        ,"U4g7qXBinlw");
    }

    public void ajout226(){
        ajouter("Expendables 2 : Unité spéciale"
        ,"2012-08-08"
        ,"Les Expendables sont de retour et cette fois, c’est pour une affaire personnelle… Barney Ross, Lee Christmas, Yin Yang, Gunnar Jensen, Toll Road et Hale Caesar (accompagnés de leurs nouveaux membres Billy the Kid et Maggie ) doivent se réunir quand Mr. Church les engagent pour ce qui a l’air d’un job facile. Cela semble de l’argent facilement gagné pour Barney et sa bande de mercenaires à l’ancienne. Mais quand les événements tournent mal et que l’un des leurs est tué, les Expendables vont crier vengeance, dans un territoire hostile où toutes les chances sont contre eux. L’équipe tente alors une percée chez les forces adverses et s’en prennent à une de leurs armes : six livres de plutonium de qualité militaire, assez pour changer la face du monde. Mais ce ne sera rien comparé à ce qu’ils réservent à celui qui a sauvagement assassiné leur frère. Les règlements de compte et cette bataille ce fera bien sûr à la manière des Expendables…"
        ,"Stallone_Sylvester"
        ,"Action,Aventure,Thriller"
        ,"PTEBUh76o-Y");
    }

    public void ajout227(){
        ajouter("Les Autres"
        ,"2001-08-02"
        ,"En 1945, la Seconde Guerre mondiale est terminée mais le mari de Grace, parti combattre, n\'est pas revenu du front. Dans une immense demeure victorienne isolée sur l\'île de Jersey, cette jeune femme pieuse élève seule ses deux enfants, Anne et Nicholas, selon les principes stricts de sa religion. Atteints d\'un mal étrange, ces derniers ne peuvent être exposés à la lumière du jour. Ils vivent donc reclus dans ce manoir qui doit constamment rester dans l\'obscurité. Lorsque trois nouveaux domestiques viennent habiter avec Grace et ses enfants, ils doivent se plier à une règle vitale : aucune porte ne doit être ouverte avant que la précédente n\'ait été fermée. Pourtant, quelqu\'un va désobéir à cet ordre. Dès lors, Grace, ses enfants et tous ceux qui les entourent devront en subir les conséquences."
        ,"Kidman_Nicole"
        ,"Horreur,Mystère,Thriller"
        ,"VeOLvPnndLs");
    }

    public void ajout228(){
        ajouter("À la croisée des mondes : La Boussole d\'or"
        ,"2007-12-04"
        ,"Lyra, 12 ans, est une orpheline rebelle qui vit à Jordan College, un établissement de l\'Université d\'Oxford, dans un monde parallèle qui ressemble au nôtre mais qui a évolué de façon un peu différente. Elle a pour compagnon Pantalaimon, son dæmon, un être capable de prendre de nombreuses formes animales. Le monde de Lyra est en train de changer. L\'organisme gouvernemental global, le Magisterium, resserre son emprise sur le peuple. Ses sombres activités l\'ont poussé à faire enlever des enfants par les mystérieux Enfourneurs. Parmi les gitans, qui ont perdu beaucoup des leurs, court une rumeur : les enfants sont emmenés dans une station expérimentale quelque part dans le Nord, et on pratique sur eux d\'abominables expériences... Lorsque Roger, le meilleur ami de Lyra, disparaît à son tour, la petite fille jure d\'aller le chercher, jusqu\'au bout du monde s\'il le faut."
        ,"Kidman_Nicole"
        ,"Aventure,Fantastique"
        ,"QLnDTHbJtXg");
    }

    public void ajout230(){
        ajouter("300"
        ,"2007-03-07"
        ,"Adapté du roman graphique de Frank Miller, 300 est un récit épique de la Bataille des Thermopyles qui opposa en l’an –480 le roi Léonidas et 300 soldats spartiates à Xerxès et l’immense armée perse. Face à un invincible ennemi, les 300 déployèrent jusqu’à leur dernier souffle un courage surhumain ; leur vaillance et leur héroïque sacrifice inspirèrent toute la Grèce à se dresser contre la Perse, posant ainsi les premières pierres de la démocratie."
        ,"Headey_Lena,Butler_Gerard"
        ,"Action,Aventure,Guerre"
        ,"1R2HpgDVrFc");
    }

    public void ajout231(){
        ajouter("American Nightmare"
        ,"2013-05-31"
        ,"Dans un futur proche, les Etats-Unis sont entrés dans une ère prospère, sans crime ni chômage. Et ce pour une unique raison : tous les ans, pendant une nuit entière, le gouvernement autorise quiconque à se livrer à une violence totale et légale. Cette nuit-là, la police ne répond à aucun appel, les hôpitaux ferment leurs portes. Cette nuit-là, alors que personne ne peut être puni et que le pays est à feu et à sang douze heures durant, une famille se retrouve au coeur de l\'horreur alors qu\'un étranger vient frapper à sa porte... Réussiront-ils à survivre à la nuit ?"
        ,"Headey_Lena"
        ,"Science-Fiction,Horreur,Thriller"
        ,"AVRk-UhPkHU");
    }

    public void ajout232(){
        ajouter("Pacific Rim : Uprising"
        ,"2018-03-21"
        ,"Le conflit planétaire qui oppose les Kaiju, créatures extraterrestres, aux Jaegers, robots géants pilotés par des humains, n’était que la première vague d’une attaque massive contre l’Humanité.  Jake Pentecost, un jeune pilote de Jaeger prometteur dont le célèbre père a sacrifié sa vie pour sauver l’Humanité des monstrueux Kaiju a depuis abandonné son entraînement et s’est retrouvé pris dans l’engrenage du milieu criminel."
        ,"Sakhno_Ivanna"
        ,"Action,Fantastique,Science-Fiction,Aventure"
        ,"ItD0FNYiX-Q");
    }

    public void ajout233(){
        ajouter("L\'Espion qui m\'a larguée"
        ,"2018-08-02"
        ,"Audrey et Morgan, deux trentenaires vivant à Los Angeles, se retrouvent embarquées malgré elles dans une conspiration internationale lorsque l\'ex-petit ami d\'Audrey débarque à son appartement poursuivi par une équipe d\'assassins. Les deux jeunes femmes sont contraintes d’échapper à leurs poursuivants à travers toute l’Europe, tout en tentant de sauver le monde avec l’aide d’un agent au charme “so british”."
        ,"Sakhno_Ivanna"
        ,"Action,Comédie"
        ,"ep6IWOKLqg4");
    }

    public void ajout234(){
        ajouter("La Pat\' Patrouille : Le Film"
        ,"2021-08-09"
        ,"La Pat’ Patrouille part en mission pour sa première grande aventure au cinéma ! Près de chez eux, leur plus grand rival, Monsieur Hellinger, devient le maire d\' Aventureville et commence à semer le trouble. C\'est à Ryder et les chiens intrépides de la Pat’ Patrouille de plonger dans l\'action pour l\'arrêter. Tandis que l\'un de nos héros se voit confronté à son passé dans cette nouvelle ville, l’équipe va trouver une nouvelle alliée : Liberty, une teckel futée et pleine d\'énergie. Équipée de gadgets inédits et de nouveaux équipements, la Pat’ Patrouille va voler au secours des citoyens d’Aventureville. Aucune mission n\'est trop dure car la Pat\' Patrouille assure !"
        ,"Armitage_Iain"
        ,"Animation,Familial,Aventure,Comédie"
        ,"aB3YeloMKVw");
    }

    public void ajout235(){
        ajouter("Scooby !"
        ,"2020-07-08"
        ,"Dans SCOOBY!, on découvre comment Scooby et Sammy, amis pour la vie, se sont rencontrés et associés aux détectives en herbe Fred, Velma et Daphné pour créer la célèbre équipe Mystère et Cie. Après avoir résolu des centaines d\'affaires et vécu d\'innombrables aventures, Scooby et sa bande doivent désormais s\'attaquer à leur énigme la plus redoutable : un complot destiné à déchaîner les forces du chien-fantôme Cerberus. Tandis qu\'ils mettent tout en œuvre pour enrayer cette \"acabocalypse\" mondiale, nos amis découvrent que Scooby est porteur d\'une lourde hérédité et qu\'il est promis à un plus grand destin que quiconque aurait pu l\'imaginer."
        ,"Armitage_Iain"
        ,"Animation,Comédie,Familial,Mystère"
        ,"1TEaxhn79Jc");
    }

    public void ajout236(){
        ajouter("Dallas Buyers Club"
        ,"2013-11-01"
        ,"1986, Dallas, Texas, une histoire vraie. Ron Woodroof a 35 ans, des bottes, un Stetson, c’est un cow‐boy, un vrai. Sa vie: sexe, drogue et rodéo. Tout bascule quand, diagnostiqué séropositif, il lui reste 30 jours à vivre. Révolté par l’impuissance du corps médical, il recourt à des traitements alternatifs non officiels. Au fil du temps, il rassemble d’autres malades en quête de guérison: le Dallas Buyers Club est né. Mais son succès gêne, Ron doit s’engager dans une bataille contre les laboratoires et les autorités fédérales. C’est son combat pour une nouvelle cause… et pour sa propre vie."
        ,"McConaughey_Matthew"
        ,"Drame,Histoire"
        ,"0BwcEvjiTZ0");
    }

    public void ajout237(){
        ajouter("Tous en scène"
        ,"2016-11-23"
        ,"Buster Moon est un élégant koala qui dirige un grand théâtre, jadis illustre, mais aujourd’hui tombé en désuétude. C’est un éternel optimiste, un peu bougon, qui aime son précieux théâtre au‐delà de tout et serait prêt à tout pour le sauver. C’est alors qu’il trouve une chance en or pour redorer son blason tout en évitant la destruction de ses rêves et de toutes ses ambitions : une compétition de chant. Cinq candidats sont retenus pour ce défi : une souris aussi séduisante que malhonnête, une jeune éléphante timide dévorée par le trac, une truie mère de famille débordée par ses 25 marcassins, un jeune gorille délinquant qui ne cherche qu’à échapper à sa famille, et une porc‐épic punk qui peine à se débarrasser de son petit ami à l’ego surdimensionné pour faire une carrière solo. Tout ce petit monde va venir chercher sur la scène de Buster l’opportunité qui pourra changer leur vie à jamais."
        ,"McConaughey_Matthew"
        ,"Animation,Comédie,Familial,Musique"
        ,"uf5gShKiZRE");
    }

    public void ajout238(){
        ajouter("Kung Fu Panda"
        ,"2008-06-04"
        ,"Passionné, costaud et quelque peu maladroit, Po est sans conteste le plus grand fan de kung‐fu. Serveur dans le restaurant de nouilles de son père, son habileté reste encore à prouver. Élu pour accomplir une ancienne prophétie, Po rejoint le monde du kung‐fu afin d’apprendre les arts martiaux auprès de ses idoles, les légendaires Cinq Cyclones : Tigresse, Grue, Mante, Vipère et Singe, sous les ordres de leur professeur et entraîneur, Maître Shifu. Mais Taï Lung, le léopard des neiges fourbe et animé d’un désir de vengeance, approche à grands pas, et c’est Po qui sera chargé de défendre la vallée face à cette menace grandissante."
        ,"Black_Jack"
        ,"Action,Aventure,Animation,Familial,Comédie"
        ,"Do6k0Gfl2a8");
    }

    public void ajout239(){
        ajouter("Kung Fu Panda 2"
        ,"2011-05-25"
        ,"Le rêve de Po s’est réalisé. Il est devenu le Guerrier Dragon, qui protège la Vallée de la Paix avec ses amis les Cinq Cyclones : Tigresse, Grue, Mante, Vipère et Singe. Mais cette vie topissime est menacée par un nouvel ennemi, décidé à conquérir la Chine et anéantir le kung‐fu à l’aide d’une arme secrète et indestructible. Comment Po pourra‐t‐il triompher d’une arme plus forte que le kung‐fu ? Il devra se tourner vers son passé et découvrir le secret de ses mystérieuses origines. Alors seulement, il pourra libérer la force nécessaire pour vaincre."
        ,"Black_Jack"
        ,"Animation,Familial"
        ,"7tSlX-unjqo");
    }

    public void ajout240(){
        ajouter("Kung Fu Panda 3"
        ,"2016-01-23"
        ,"Po avait toujours cru son père panda disparu, mais le voilà qui réapparaît ! Enfin réunis, père et fils vont voyager jusqu’au paradis secret du peuple panda. Ils y feront la connaissance de certains de leurs semblables, tous plus déjantés les uns que les autres. Mais lorsque le maléfique Kaï décide de s’attaquer aux plus grands maîtres de kung‐fu à travers toute la Chine, Po va devoir réussir l’impossible : transformer tout un village de pandas maladroits et rigolards en experts des arts martiaux, les redoutables Kung‐Fu Pandas !"
        ,"Black_Jack"
        ,"Action,Aventure,Animation,Comédie,Familial"
        ,"SYSwGpetP6Q");
    }

    public void ajout241(){
        ajouter("Mary"
        ,"2017-04-12"
        ,"Un homme se bat pour obtenir la garde de sa nièce, qui témoigne d\'un don hors du commun pour les mathématiques."
        ,"Grace_Mckenna"
        ,"Drame,Comédie"
        ,"zCt2zl2j59o");
    }

    public void ajout242(){
        ajouter("S.O.S. Fantômes : L’Héritage"
        ,"2021-11-11"
        ,"Une mère célibataire et ses deux enfants s\'installent dans une petite ville et découvrent peu à peu leur relation avec les chasseurs de fantômes et l\'héritage légué par leur grand-père."
        ,"Grace_Mckenna"
        ,"Fantastique,Comédie,Aventure"
        ,"-Q8uXZHWSV0");
    }

    public void ajout243(){
        ajouter("Caligula"
        ,"1979-08-14"
        ,"La vie de Caligula, Empereur de Rome, sur fond d\'orgies et d\'intrigues. Une reconstitution historique impressionante de la décadence érotique romaine au Ier siècle de notre ère."
        ,"Brass_Tinto"
        ,"Drame,Histoire,Romance"
        ,"QMBD5ANB7V0");
    }

    public void ajout244(){
        ajouter("Paprika"
        ,"1991-02-13"
        ,"En 1958, Mimma, une jeune femme plantureuse jouée par Debora Caprioglio, décide de travailler dans un bordel pour une courte période afin d\'arrondir ses fins de mois et d\'aider son fiancé Nino. Elle décide de se faire appeler Paprika après avoir goûté un goulash."
        ,"Brass_Tinto"
        ,"Drame"
        ,"uQomVnBkydc");
    }

    public void ajout245(){
        ajouter("La clef"
        ,"1983-10-19"
        ,"En 1940, un homme déclinant et libertin tient le journal de ses frustrations et de ses fantasmes avec le désir inavoué que sa très belle épouse le lise. Cela ne manque pas de se produire et la jeune femme passe à l\'acte avec son gendre, tout en rédigeant à son tour un journal qui répond à celui de son mari."
        ,"Brass_Tinto"
        ,"Drame,Romance"
        ,"J53wZ3Kksqw");
    }

    public void ajout246(){
        ajouter("Doctor Strange"
        ,"2016-10-25"
        ,"Doctor Strange suit l’histoire du Docteur Stephen Strange, talentueux neurochirurgien qui, après un tragique accident de voiture, doit mettre son ego de côté et apprendre les secrets d’un monde caché de mysticisme et de dimensions alternatives. Basé à New York, dans le quartier de Greenwich Village, Doctor Strange doit jouer les intermédiaires entre le monde réel et ce qui se trouve au‐delà, en utilisant un vaste éventail d’aptitudes métaphysiques et d’artefacts pour protéger le Marvel Cinematic Universe."
        ,"Mikkelsen_Mads"
        ,"Action,Aventure,Fantastique"
        ,"bly7XM7QzSY");
    }

    public void ajout247(){
        ajouter("La Chasse"
        ,"2012-10-25"
        ,"Après un divorce difficile, Lucas, quarante ans, a trouvé une nouvelle petite amie, un nouveau travail et il s\'applique à reconstruire sa relation avec Marcus, son fils adolescent. Mais quelque chose tourne mal. Presque rien. Une remarque en passant. Un mensonge fortuit. Et alors que la neige commence à tomber et que les lumières de Noël s\'illuminent, le mensonge se répand comme un virus invisible. La stupeur et la méfiance se propagent et la petite communauté plonge dans l\'hystérie collective, obligeant Lucas à se battre pour sauver sa vie et sa dignité."
        ,"Mikkelsen_Mads"
        ,"Drame"
        ,"TRoUmIztJ6M");
    }

    public void ajout248(){
        ajouter("Fast & Furious 6"
        ,"2013-05-21"
        ,"Dom, Brian et toute leur équipe, après le casse de Rio, ayant fait tomber un empire en empochant 100 millions de dollars, se sont dispersés aux quatre coins du globe. Mais l’incapacité de rentrer chez eux, et l’obligation de vivre en cavale permanente, laissent à leur vie le goût amer de l’inaccomplissement. Pendant ce temps Hobbs traque aux 4 coins du monde un groupe de chauffeurs mercenaires aux talents redoutables, dont le meneur, Shaw est secondé d’une de main de fer par l’amour que Dom croyait avoir perdu pour toujours : Letty. La seule façon d’arrêter leurs agissements est de les détrôner en surpassant leur réputation. Hobbs demande donc à Dom de rassembler son équipe de choc à Londres. En retour ? Ils seront tous graciés et pourront retourner auprès des leurs, afin de vivre une vie normale."
        ,"Diesel_Vin"
        ,"Action,Thriller,Crime"
        ,"Ewu0WTPbOVY");
    }

    public void ajout249(){
        ajouter("Fast & Furious 7"
        ,"2015-04-01"
        ,"Laissé à moitié mort par Dominic Toretto et son clan de voleur de voitures as du volant, le criminel Owen Shaw gît sur un lit d\'hôpital à Londres. Déterminé à le venger, son frère Deckard Shaw commet des attaques vicieuses contre les membres de la famille de Toretto, notamment contre sa sœur, son beau-frère et leur petit garçon. Une aide inespérée leur parvient en la personne de Frank Petty, agent des services secrets américains aux ressources impressionnantes. Il promet assistance à Dominic, en échange de quoi ce dernier devra libérer une talentueuse informaticienne, nommée Ramsey, qui a mis au point un logiciel de surveillance ultraperfectionné baptisé « God\'s Eye ». La jeune femme est en effet retenue prisonnière par Jakande, un dangereux terroriste, au milieu des inaccessibles montagnes de l\'Azerbaïdjan. Le clan de Dominic se reforme donc pour cette dangereuse mission."
        ,"Diesel_Vin"
        ,"Action,Thriller,Crime"
        ,"3Wu2BhFuc4c");
    }

    public void ajout250(){
        ajouter("Fast & Furious 8"
        ,"2017-04-12"
        ,"Maintenant que Dom et Letty sont en lune de miel, que Brian et Mia se sont rangés et que le reste de l’équipe a été disculpé, la bande de globe-trotteurs retrouve un semblant de vie normale. Mais quand une mystérieuse femme entraîne Dom dans le monde de la criminalité, ce dernier ne pourra éviter de trahir ses proches qui vont faire face à des épreuves qu’ils n’avaient jamais rencontrées jusqu’alors.  Des rivages de Cuba aux rues de New York en passant par les plaines gelées de la mer arctique de Barrents, notre équipe va sillonner le globe pour tenter d\'empêcher une anarchiste de déchaîner un chaos mondial et de ramener à la maison l’homme qui a fait d’eux une famille."
        ,"Diesel_Vin"
        ,"Action,Crime,Thriller"
        ,"xFO-pKB13mw");
    }

    public void ajout251(){
        ajouter("Gladiator"
        ,"2000-05-04"
        ,"Le général romain Maximus est le plus fidèle soutien de l\'empereur Marc Aurèle, qu\'il a conduit de victoire en victoire avec une bravoure et un dévouement exemplaires. Jaloux du prestige de Maximus, et plus encore de l\'amour que lui voue l\'empereur, le fils de Marc Aurèle, Commode, s\'arroge brutalement le pouvoir, puis ordonne l\'arrestation du général et son exécution. Maximus échappe à ses assassins mais ne peut empêcher le massacre de sa famille. Capturé par un marchand d\'esclaves, il devient gladiateur et prépare sa vengeance."
        ,"Crowe_Russell"
        ,"Action,Drame,Aventure"
        ,"ChcgxBAzrks");
    }

    public void ajout252(){
        ajouter("The Nice Guys"
        ,"2016-05-15"
        ,"Los Angeles. Années 70. Deux détectives privés enquêtent sur le prétendu suicide d’une starlette. Malgré des méthodes pour le moins « originales », leurs investigations vont mettre à jour une conspiration impliquant des personnalités très haut placées…"
        ,"Crowe_Russell"
        ,"Comédie,Crime,Action"
        ,"JrKaIU9Hjc4");
    }

    public void ajout253(){
        ajouter("Grease"
        ,"1978-07-07"
        ,"A la fin des vacances d\'été, les amoureux Danny Zuko et Sandy Olsson, une jeune Australienne de bonne famille, doivent se séparer. A son retour au lycée Rydell, le jeune homme retrouve sa bande, les T-birds, blousons de cuir et cheveux gominés. Les parents de Sandy ayant décidé de s\'installer aux Etats-Unis, la demoiselle intègre la même école... Passé la surprise des retrouvailles et pour faire bonne figure devant ses copains, Danny adopte une attitude désinvolte qui laisse la jeune fille totalement désemparée. Sandy rejoint alors les Pink Ladies, le pendant féminin des T-Birds. S\'ensuit un jeu du chat et de la souris entre les deux tourtereaux, le tout rythmé par les événements de leur vie de lycéens : démarrage de la saison de football américain, bal de promotion, course de voitures, soirées entre filles, entre garçons, au fast-food, au drive-in..."
        ,"Travolta_John"
        ,"Romance,Comédie"
        ,"THd96gHV7Tg");
    }

    public void ajout254(){
        ajouter("Volt, star malgré lui"
        ,"2008-11-21"
        ,"Volt est le héros de sa propre série télévisée. Mais le toutou ne connaît que les studios de cinéma, et lorsqu\'il se retrouve perdu dans la ville de New-York, à la recherche de sa maîtresse Penny, Volt reste persuadé que ses super-pouvoirs télévisés sont réels."
        ,"Travolta_John"
        ,"Animation,Familial,Aventure,Comédie"
        ,"qdfA3BQMW40");
    }

    public void ajout255(){
        ajouter("La Momie"
        ,"1999-04-16"
        ,"Longtemps avant la naissance du Christ, pour avoir osé défier le pharaon en lui ravissant sa jeune maîtresse, le grand prêtre de Thèbes, Imhotep, est momifié vivant et enseveli dans une crypte secrète d\'Hamunaptra, la cité des morts. Du fond de son sarcophage, le grand prêtre jure de se venger du genre humain. En 1923, un aventurier américain, Rick O\'Connell, découvre fortuitement les ruines d\'Hamunaptra que des générations d\'égyptologues recherchaient en vain. Il met dans le secret une jeune égyptologue et son frère et tous trois partent à la recherche du trésor des pharaons."
        ,"Weisz_Rachel"
        ,"Aventure,Action,Fantastique"
        ,"k1gEdeF3Ka4");
    }

    public void ajout256(){
        ajouter("Constantine"
        ,"2005-02-08"
        ,"John Constantine, extralucide anticonformiste, qui a littéralement fait un aller-retour aux enfers, doit aider Katelin Dodson, une policière incrédule, à lever le voile sur le suicide mystérieux de sa sœur jumelle. Cette enquête leur fera découvrir l\'univers d\'anges et de démons qui hantent les sous-sols de Los Angeles d\'aujourd\'hui."
        ,"Weisz_Rachel"
        ,"Fantastique,Action,Horreur"
        ,"2jfDHq2bq_g");
    }

    public void ajout257(){
        ajouter("Le Retour de la momie"
        ,"2001-05-04"
        ,"En 1935, Rick O’Connell et sa femme Evelyn mènent une vie paisible à Londres avec Alex, leur fils talentueux. Mais une nouvelle catastrophe se prépare dans les profondeurs du désert saharien. Six mille ans avant notre ère, le roi Scorpion, fit un pacte avec le dieu Anubis, qu’il trahit. Damné pour l’éternité, il est sur le point de sortir des limbes et de lever l’armée d’Anubis pour dévaster la planète. Une secte mystérieuse et avide de pouvoir menée par le diabolique Lock Nah et la séduisante Meela, ramènent à la vie la seule créature capable de faire face au roi Scorpion : le grand prêtre Imhotep, condamné depuis trois mille ans à errer comme un mort‐vivant pour avoir séduit la favorite du Pharaon. Sa momie est entreposée dans une salle secrète du British Museum. Ardeth Bay, chef militaire des Medja, implore O’Connell d’empêcher ce forfait aux conséquences désastreuses."
        ,"Weisz_Rachel"
        ,"Aventure,Action,Fantastique"
        ,"p-WKyZAB9pY");
    }

    public void ajout258(){
        ajouter("Independence Day"
        ,"1996-06-25"
        ,"Une immense soucoupe volante envahit le ciel terrestre, libérant un nombre infini de plus petites soucoupes qui prennent position au-dessus des plus grandes villes du monde. Un informaticien new-yorkais décrypte les signaux émanant des étranges voyageurs. Ils ne sont pas du tout amicaux et ces extraterrestres se préparent à attaquer la Terre."
        ,"McDonnell_Mary"
        ,"Action,Aventure,Science-Fiction"
        ,"ztv8HJuPyL4");
    }

    public void ajout259(){
        ajouter("Donnie Darko"
        ,"2001-10-24"
        ,"Donnie Darko est un adolescent de seize ans pas comme les autres. Intelligent et doté d\'une grande imagination, il a pour ami Frank, une créature que lui seul peut voir et entendre. Lorsque Donnie survit par miracle à un accident, Frank lui propose un étrange marché. La fin du monde approche et ce dernier doit accomplir sa destinée. Des événements bizarres surviennent dans la petite ville tranquille, mais Donnie sait que derrière tout cela se cachent d\'inavouables secrets. Frank l\'aidera à les mettre à jour, semant ainsi le trouble au sein de la communauté."
        ,"McDonnell_Mary"
        ,"Fantastique,Drame,Mystère"
        ,"j9Us2Ga3xIE");
    }

    public void ajout263(){
        ajouter("Le Chardonneret"
        ,"2019-09-12"
        ,"Theodore \"Theo\" Decker n\'a que 13 ans quand sa mère est tuée dans une explosion au Metropolitan Museum of Art. Cette tragédie va bouleverser sa vie : passant de la détresse à la culpabilité, il se reconstruit peu à peu et découvre même l\'amour. Tout au long de son périple vers l\'âge adulte, il conserve précieusement une relique de ce jour funeste qui lui permet de ne pas perdre espoir : un tableau d\'un minuscule oiseau enchaîné à son perchoir. Le Chardonneret."
        ,"Fitzgerald_Willa"
        ,"Drame"
        ,"4oBqSzUPoAg");
    }

    public void ajout265(){
        ajouter("Skyfall"
        ,"2012-10-24"
        ,"Lorsque la dernière mission de Bond tourne mal, plusieurs agents infiltrés se retrouvent exposés dans le monde entier. Le MI6 est attaqué, et M est obligée de relocaliser l’Agence. Ces événements ébranlent son autorité, et elle est remise en cause par Mallory, le nouveau président de l’ISC, le comité chargé du renseignement et de la sécurité. Le MI6 est à présent sous le coup d’une double menace, intérieure et extérieure. Il ne reste à M qu’un seul allié de confiance vers qui se tourner: Bond. Plus que jamais, 007 va devoir agir dans l’ombre. Avec l’aide d’Ève, un agent de terrain, il se lance sur la piste du mystérieux Silva, dont il doit identifier coûte que coûte l’objectif secret et mortel."
        ,"Cheung_Yennis"
        ,"Action,Aventure,Thriller"
        ,"WKzm6AZFbdc");
    }

    public void ajout266(){
        ajouter("Course à la mort 4 : Au-delà de l\'anarchie"
        ,"2018-10-02"
        ,"Alors considérée comme illégale, «La Course à la Mort» se pratique toujours dans une prison fédérale. Après une attaque manquée contre le légendaire pilote Frankenstein, Connor Gibson, membre d’une unité d’élite doit infiltrer la prison avec un objectif : stopper «La Course à la Mort». Il devra alors apprendre se battre dans un monde sans foi ni loi…"
        ,"Cheung_Yennis"
        ,"Action,Thriller,Science-Fiction"
        ,"BY0_gbpZI5o");
    }

    public void ajout267(){
        ajouter("Muse"
        ,"2017-11-09"
        ,"Samuel Salomon, un professeur de littérature, s\'est absenté durant 1 an après le décès de sa fiancée. Depuis, chaque nuit, il fait un cauchemar dans lequel une femme est assassinée selon un étrange rituel. Quand cette femme est retrouvée morte dans la même position que le rêve de Samuel, ce dernier décide de se rendre sur la scène du crime. Il y fait la rencontre de Rachel, qui fait chaque nuit le même rêve que lui. Ensemble ils vont tenter de découvrir l\'identité de cette mystérieuse femme et entrer dans un monde terrifiant contrôlé par Les Muses."
        ,"Cheung_Yennis"
        ,"Thriller,Horreur"
        ,"PrfIgmh8pcE");
    }

    public void ajout268(){
        ajouter("The Grand Budapest Hotel"
        ,"2014-02-26"
        ,"Pendant l’entre‐deux guerres, le légendaire concierge d’un grand hôtel et son jeune protégé se retrouvent impliqués dans une histoire mêlant le vol d’un tableau de la Renaissance, la bataille pour une énorme fortune familiale, et le lent puis soudain bouleversement qui transforme l’Europe en cette première moitié de XXème siècle."
        ,"Stevens_Fisher,Fiennes_Ralph"
        ,"Comédie,Drame"
        ,"Lo1nz-scO4Q");
    }

    public void ajout269(){
        ajouter("L\'Île aux chiens"
        ,"2018-03-23"
        ,"Lorsqu’une épidémie de grippe canine envahit la ville japonaise de Megasaki et menace de contaminer les hommes, le maire ordonne la mise en quarantaine de tous les chiens. L’île poubelle devient : l’Île aux Chiens. Un jeune garçon de 12 ans, Atari, se rend sur place à la recherche de son chien perdu, Spots. C’est alors qu’il fait la découverte,  à l’aide d’une meute de cinq chiens, d’une conspiration qui menace la ville."
        ,"Stevens_Fisher"
        ,"Aventure,Comédie,Animation"
        ,"WOGWWxBgCUI");
    }

    public void ajout270(){
        ajouter("Short Circuit"
        ,"1986-05-09"
        ,"Robot participant à une expérience scientifique, Numéro 5 développe une intelligence artificielle et des caractéristiques humaines après un court-circuit. Il essaie d\'échapper à son condition de robot et s\'enfuit. Les autorités organisent alors la traque..."
        ,"Stevens_Fisher"
        ,"Comédie,Familial,Science-Fiction"
        ,"JffI4xQOyUs");
    }

    public void ajout271(){
        ajouter("Un amour à New York"
        ,"2001-10-05"
        ,"A l\'approche des fêtes de fin d\'année, en 1990, Jonathan Trager croise dans la foule new-yorkaise Sara, une ravissante jeune femme. C\'est le coup de foudre. Bien que tous deux soient engagés dans une autre relation, Jonathan et Sara passent la nuit à errer ensemble dans Manhattan. Mais la nuit touche à sa fin et les voilà contraints de prendre la décision de se revoir au non. Quand Jonathan, sous le charme, propose un échange de numéros de téléphone, Sara se dérobe pour suggérer de laisser le destin décider. S\'ils sont faits l\'un pour l\'autre, dit-elle, ils trouveront bien le moyen de se revoir. Dix ans plus tard, les deux jeunes gens sont sur le point de se marier avec quelqu\'un d\'autre. Cette fois, le moment est venu pour eux de pousser la curiosité plus loin. Se rappelant de cette rencontre magique, ils décident de se retrouver avec l\'aide de leurs meilleurs amis."
        ,"Piven_Jeremy"
        ,"Comédie,Romance,Drame"
        ,"Ubd3NGHX9Sc");
    }

    public void ajout272(){
        ajouter("Mademoiselle Détective"
        ,"2012-12-06"
        ,"Molly, une adolescente qui joue au détective amateur, est engagée par le FBI pour protéger une fille dont le père doit témoigner à un procès. Elle est donc envoyée dans le Sud des États-Unis sous couverture afin d\'intégrer l\'université et la confrérie de la jeune fille. Garçon manqué à cause de son éducation, Molly va avoir beaucoup de mal à passer inaperçu et sa mission ne va pas être de tout repos."
        ,"Piven_Jeremy"
        ,"Action,Comédie"
        ,"Q35fG7dOwCU");
    }

    public void ajout273(){
        ajouter("Le Parrain, 2e partie"
        ,"1974-12-20"
        ,"Depuis la mort de Don Vito Corleone, son fils Michael règne sur la famille. Amené à négocier avec la mafia juive, il perd alors le soutien d\'un de ses lieutenants, Frankie Pentageli. Echappant de justesse à un attentat, Michael tente de retrouver le coupable, soupçonnant Hyman Roth, le chef de la mafia juive. Vito Corleone, immigrant italien, arrive à New York au début du siècle ; très vite, il devient un des caïds du quartier, utilisant la violence comme moyen de régler toutes les affaires. Seul au départ, il bâtit peu à peu un véritable empire, origine de la fortune de la famille des Corleone."
        ,"Pacino_Al"
        ,"Drame,Crime"
        ,"bRZPf3IKZ_4");
    }

    public void ajout274(){
        ajouter("Scarface"
        ,"1983-12-09"
        ,"En 1980, le gouvernement cubain expulse plusieurs centaines de prisonniers dangereux vers la Floride. Parmi eux, Tony Montana : ambitieux et sans scrupules, il élabore un plan pour prendre la place d\'un caïd de la drogue."
        ,"Pacino_Al"
        ,"Action,Crime,Drame"
        ,"3VNLz5pHq74");
    }

    public void ajout275(){
        ajouter("Le Parrain"
        ,"1972-03-14"
        ,"La Seconde Guerre mondiale vient de s\'achever. À New York, le « parrain » Don Corleone, l\'un des chefs respectés de la mafia, se sent vieillir. Il refuse de s\'adapter à son temps et de se lancer, comme ses pairs, dans le trafic de drogue. Une frilosité qui entrave la bonne marche des affaires des autres « familles » et qui lui vaut d\'être la cible d\'un attentat. Don Corleone survit à ses blessures, mais reste très diminué. Mike, son plus jeune fils, qui jusque-là se tenait à l\'écart des affaires de son père, devient le plus dévoué de ses héritiers. Plus efficace que ses frères, Sonny et Fredo, il venge son père et organise l\'élimination de ses adversaires…"
        ,"Pacino_Al"
        ,"Drame,Crime"
        ,"bmtuIhesQWA");
    }

    public void ajout276(){
        ajouter("Birds of Prey et la fantabuleuse histoire de Harley Quinn"
        ,"2020-02-05"
        ,"Vous connaissez l’histoire du flic, de l’oiseau chanteur, de la cinglée et de la princesse mafieuse ?  Birds of Prey et la fantabuleuse histoire de Harley Quinn est une histoire déjantée racontée par Harley en personne, d’une manière dont elle seule a le secret.  Roman Sionis, l’ennemi le plus abominable et le plus narcissique de Gotham, ainsi que son fidèle acolyte Zsasz décident de s\'en prendre à une certaine « Cass ». La ville est passée au peigne fin pour retrouver la trace de la jeune fille.  Les parcours de Harley, de la Chasseuse, de Black Canary et de Renee Montoya se télescopent et ce quatuor improbable n’a d’autre choix que de faire équipe pour éliminer Roman…"
        ,"Robbie_Margot"
        ,"Action,Crime"
        ,"6bh1OHkIuYA");
    }

    public void ajout277(){
        ajouter("The Suicide Squad"
        ,"2021-07-28"
        ,"Bienvenue en enfer - aka Belle Reve, la prison dotée du taux de mortalité le plus élevé des États-Unis d\'Amérique. Là où sont détenus les pires super-vilains, qui feront tout pour en sortir - y compris rejoindre la super secrète et la super louche Task Force X. La mission mortelle du jour ? Assemblez une belle collection d\'escrocs, et notamment Bloodsport, Peacemaker, Captain Boomerang, Ratcatcher 2, Savant, King Shark, Blackguard, Javelin et la psychopathe préférée de tous : Harley Quinn. Armez-les lourdement et jetez-les (littéralement) sur l\'île lointaine et bourrée d\'ennemis de Corto Maltese. Traversant une jungle qui grouille d\'adversaires et de guerilleros à chaque tournant, l\'Escouade est lancée dans une mission de recherche et de destruction, avec le seul Colonel Rick Flag pour les encadrer sur le terrain et la technologie du gouvernement dans leurs oreilles, afin qu\'Amanda Waller puisse suivre le moindre de leurs mouvements."
        ,"Robbie_Margot"
        ,"Action,Comédie,Aventure"
        ,"CxNnQCsRSK0");
    }

    public void ajout278(){
        ajouter("Le Loup de Wall Street"
        ,"2013-12-25"
        ,"L\'histoire vraie de Jordan Belfort, un courtier en bourse qui passa vingt mois en prison pour avoir refusé de participer à une gigantesque arnaque, dévoilant la corruption et l\'implication de la pègre qui sévissait à Wall Street et au-delà des États-Unis. L’argent, le pouvoir, les femmes, la drogue, les tentations étaient là, à portée de main, et les autorités n’avaient aucune prise. Aux yeux de Jordan et de sa meute, la modestie était devenue complètement inutile. Trop n’était jamais assez…"
        ,"Robbie_Margot,Scorsese_Martin"
        ,"Crime,Drame,Comédie"
        ,"GT9UfSqBz9o");
    }

    public void ajout279(){
        ajouter("Défense d\'atterrir"
        ,"2022-08-03"
        ,"Une attaque terroriste menée en plein vol. Des passagers touchés l’un après l’autre. Un flic chargé de l’enquête, dont l’épouse est dans l’avion. Une compagnie aérienne dépassée. Une seule consigne adressée aux pilotes : Défense d’atterrir."
        ,"Si-won_Lee"
        ,"Action,Drame,Thriller"
        ,"V4-bO1yrAW8");
    }

    public void ajout280(){
        ajouter("Saw II"
        ,"2005-10-28"
        ,"Chargé de l’enquête autour d’une mort sanglante, l’Inspecteur Eric Mason est persuadé que le crime est l’œuvre du redoutable Jigsaw, un criminel machiavélique qui impose à ses victimes des choix auxquels personne ne souhaite jamais être confronté. Cette fois‐ci, ce ne sont plus deux mais huit personnes qui ont été piégées par Jigsaw…"
        ,"Bell_Tobin"
        ,"Horreur"
        ,"RzsCFIbUoVQ");
    }

    public void ajout281(){
        ajouter("Saw III"
        ,"2006-10-26"
        ,"Le Tueur au puzzle a mystérieusement échappé à ceux qui pensaient le tenir. Pendant que la police se démène pour tenter de remettre la main dessus, le génie criminel a décidé de reprendre son jeu terrifiant avec l’aide de sa protégée, Amanda… Le docteur Lynn Denlon et Jeff ne le savent pas encore, mais ils sont les nouveaux pions de la partie qui va commencer…"
        ,"Bell_Tobin"
        ,"Horreur,Thriller,Crime"
        ,"91B113M4usE");
    }

    public void ajout282(){
        ajouter("Saw IV"
        ,"2007-10-25"
        ,"Le Tueur au puzzle et sa protégée, Amanda, ont disparu, mais la partie continue. Après le meurtre de l’inspectrice Kerry, deux profileurs chevronnés du FBI, les agents Strahm et Perez, viennent aider le détective Hoffman à réunir les pièces du dernier puzzle macabre laissé par le Tueur pour essayer, enfin, de comprendre. C’est alors que le commandant du SWAT, Rigg, est enlevé… Forcé de participer au jeu mortel, il n’a que 90 minutes pour triompher d’une série de pièges machiavéliques et sauver sa vie. En cherchant Rigg à travers la ville, le détective Hoffman et les deux profileurs vont découvrir des cadavres et des indices qui vont les conduire à l’ex‐femme du Tueur, Jill. L’histoire et les véritables intentions du Tueur au puzzle vont peu à peu être dévoilées, ainsi que ses plans sinistres pour ses victimes passées, présentes… et futures."
        ,"Bell_Tobin"
        ,"Horreur,Thriller,Crime"
        ,"Qe8ZSX2NW0A");
    }

    public void ajout283(){
        ajouter("Deadpool"
        ,"2016-02-09"
        ,"Deadpool, est l\'anti-héros le plus atypique de l\'univers Marvel. À l\'origine, il s\'appelle Wade Wilson : un ancien militaire des Forces Spéciales devenu mercenaire. Après avoir subi une expérimentation hors norme qui va accélérer ses pouvoirs de guérison, il va devenir Deadpool. Armé de ses nouvelles capacités et d\'un humour noir survolté, Deadpool va traquer l\'homme qui a bien failli anéantir sa vie."
        ,"Reynolds_Ryan"
        ,"Action,Aventure,Comédie"
        ,"XWtH7anz7io");
    }

    public void ajout284(){
        ajouter("Deadpool 2"
        ,"2018-05-10"
        ,"Deadpool se voit contraint de rejoindre les X-Men : après une tentative ratée de sauver un jeune mutant au pouvoir destructeur, il est jeté en prison anti-mutants. Arrive Cable, un soldat venant du futur et ayant pour cible le jeune mutant, en quête de vengeance. Deadpool décide de le combattre. Peu convaincu par les règles des X-Men, il crée sa propre équipe, la « X-Force ». Mais cette mission lui réservera de grosses surprises, des ennemis de taille et des alliés indispensables."
        ,"Reynolds_Ryan"
        ,"Action,Comédie,Aventure"
        ,"QgzslSsSECM");
    }

    public void ajout285(){
        ajouter("Free Guy"
        ,"2021-08-11"
        ,"Un employé de banque, découvrant un jour qu’il n’est en fait qu’un personnage d’arrière-plan dans un jeu vidéo en ligne, décide de devenir le héros de sa propre histoire, quitte à la réécrire. Évoluant désormais dans un monde qui ne connaît pas de limites, il va tout mettre en œuvre pour le sauver à sa manière, avant qu’il ne soit trop tard…"
        ,"Reynolds_Ryan"
        ,"Comédie,Aventure,Science-Fiction"
        ,"4P6-TMgrDXg");
    }

    public void ajout286(){
        ajouter("Avengers"
        ,"2012-04-25"
        ,"Lorsque la sécurité et l’équilibre de la planète sont menacés par un ennemi d’un genre nouveau, Nick Fury, le directeur du SHIELD, l’agence internationale du maintien de la paix, réunit une équipe pour empêcher le monde de basculer dans le chaos. Partout sur Terre, le recrutement des nouveaux héros dont le monde a besoin commence…"
        ,"Downey_Robert"
        ,"Science-Fiction,Action,Aventure"
        ,"b-kTeJhHOhc");
    }

    public void ajout287(){
        ajouter("Avengers : Infinity War"
        ,"2018-04-25"
        ,"Les Avengers et leurs alliés devront être prêts à tout sacrifier pour neutraliser le redoutable Thanos avant que son attaque éclair ne conduise à la destruction complète de l’univers."
        ,"Downey_Robert,Klementieff_Pom"
        ,"Aventure,Action,Science-Fiction"
        ,"eIWs2IUr3Vs");
    }

    public void ajout288(){
        ajouter("Iron Man"
        ,"2008-04-30"
        ,"Tony Stark, inventeur de génie, vendeur d\'armes et playboy milliardaire, est kidnappé. Forcé par ses ravisseurs de fabriquer une arme redoutable, il construit en secret une armure high-tech révolutionnaire qu\'il utilise pour s\'échapper. Comprenant la puissance de cette armure, il décide de l\'améliorer et de l\'utiliser pour faire régner la justice et protéger les innocents."
        ,"Downey_Robert"
        ,"Action,Science-Fiction,Aventure"
        ,"rDCTb9Gp2qk");
    }

    public void ajout289(){
        ajouter("Sicario : La Guerre des cartels"
        ,"2018-06-27"
        ,"Quelques années après les événements de Sicario, la lutte contre le trafic de drogue est toujours aussi âpre sur la frontière, et les cartels sont plus puissants que jamais. Matt et Alejandro sont à nouveau dépêchés sur place pour retrouver Carlos Reyes, un ponte des cartels qui s\'est lancé dans d\'autres affaires lucratives : il traite désormais avec les terroristes. Ce qui aurait dû être une opération coup de poing rondement menée se révèle en fait un sombre et tortueux combat orchestré dans l\'ombre par la CIA…"
        ,"del Toro_Benicio"
        ,"Action,Crime,Thriller"
        ,"W-fZg709doU");
    }

    public void ajout290(){
        ajouter("The French Dispatch"
        ,"2021-10-21"
        ,"Le journal américain The Evening Sun de Liberty dans le Kansas possède une antenne nommée The French Dispatch à Ennui-sur-Blasé, une ville française fictive évoquant Paris dans les années 1950-60. Arthur Howitzer Jr., le rédacteur en chef du French Dispatch, meurt subitement d\'une crise cardiaque. Selon les souhaits exprimés dans son testament, la publication du journal est immédiatement suspendue après un dernier numéro d\'adieu, dans lequel trois articles des éditions précédentes du journal sont republiés, ainsi qu\'une nécrologie : Les trois articles traitent de Moses Rosenthaler, un détenu psychopathe qui se révèle être un grand artiste peintre, des évènements de Mai 68 et enfin d\'une enquête gastronomique qui vire au polar."
        ,"del Toro_Benicio"
        ,"Drame,Comédie"
        ,"w1GFLATs2Qw");
    }

    public void ajout291(){
        ajouter("Thor : Le Monde des ténèbres"
        ,"2013-10-30"
        ,"Thor se bat pour restaurer l’ordre à travers l’univers… Mais une ancienne race menée par le menaçant Malekith revient pour précipiter l’univers dans les ténèbres. Face à un ennemi auquel même Odin et Asgard ne peuvent résister, Thor doit s’embarquer dans son plus périlleux voyage jusqu’à ce jour, au cours duquel il retrouvera Jane Foster et sera contraint de tout sacrifier pour sauver l\'humanité."
        ,"Hemsworth_Chris"
        ,"Action,Aventure,Fantastique"
        ,"uq4OFEwflnI");
    }

    public void ajout292(){
        ajouter("Equalizer 2"
        ,"2018-07-19"
        ,"Robert McCall sert inlassablement la justice au nom des exploités et des opprimés. Mais jusqu’où est-il prêt à aller lorsque cela touche quelqu’un qu’il aime ?"
        ,"Pascal_Pedro"
        ,"Action,Thriller,Crime"
        ,"TssXLyy6Gsc");
    }

    public void ajout293(){
        ajouter("Oblivion"
        ,"2013-04-10"
        ,"2077, Jack Harper, en station sur la planète Terre dont toute la population a été évacuée, est en charge de la sécurité et de la réparation des drones. Suite à des décennies de guerre contre une force extra-terrestre terrifiante qui a ravagé la Terre, Jack fait partie d’une gigantesque opération d’extraction des dernières ressources nécessaires à la survie des siens. Sa mission touche à sa fin. Dans à peine deux semaines, il rejoindra le reste des survivants dans une colonie spatiale à des milliers de kilomètres de cette planète dévastée qu’il considère néanmoins comme son chez-lui. Vivant et patrouillant à très haute altitude de ce qu’il reste de la Terre, la vie \"céleste\" de Jack est bouleversée quand il assiste au crash d’un vaisseau spatial et décide de porter secours à la belle inconnue qu’il renferme..."
        ,"Cruise_Tom"
        ,"Action,Science-Fiction,Aventure,Mystère"
        ,"XH1su3FVEA4");
    }

    public void ajout294(){
        ajouter("Mission : Impossible - Protocole Fantôme"
        ,"2011-12-07"
        ,"Après avoir été impliquée dans un complot terroriste mondial, l’agence Mission Impossible est contrainte de cesser ses activités. Le Protocole Fantôme est mis en place : Ethan Hunt et sa nouvelle équipe doivent opérer dans le secret pour blanchir le nom de leur organisation. Aucune aide, aucun contact, isolation totale. Cette mission va s\'avérer être la plus dangereuse et la plus intense qu\'ils aient eu à remplir."
        ,"Cruise_Tom"
        ,"Action,Thriller,Aventure"
        ,"9RzsgPoR9Ws");
    }

    public void ajout295(){
        ajouter("Nowhere"
        ,"2023-09-29"
        ,"Enceinte, Mia fuit avec son mari un pays totalitaire en se cachant dans un conteneur maritime. Séparée de son compagnon, la jeune femme doit se battre pour survivre lorsqu\'une violente tempête la jette à la mer. Seule et à la dérive au milieu de l\'océan, Mia va devoir relever de nombreux défis pour sauver sa fille et retrouver l\'homme de sa vie."
        ,"Castillo_Anna"
        ,"Thriller,Drame"
        ,"Mcu-62zd4TU");
    }

    public void ajout296(){
        ajouter("Holy Camp !"
        ,"2017-09-29"
        ,"Dans cette comédie musicale, deux ados rebelles fans de musique électronique vivent une expérience hors du commun lors d\'une colonie de vacances religieuse."
        ,"Castillo_Anna"
        ,"Comédie,Romance"
        ,"WJooYRR2vCQ");
    }

    public void ajout297(){
        ajouter("Adú"
        ,"2020-01-31"
        ,"Trois histoires en provenance de Melilla, à la frontière hispano-marocaine, où des migrants risquent leurs vies pour franchir le détroit de Gibraltar."
        ,"Castillo_Anna"
        ,"Drame"
        ,"_iOQ4uBvACU");
    }

    public void ajout298(){
        ajouter("Percy Jackson : Le Voleur de foudre"
        ,"2010-02-01"
        ,"Un jeune homme découvre qu\'il est le descendant d\'un dieu grec et s\'embarque, avec l\'aide d\'un satyre et de la fille d\'Athena, dans une dangereuse aventure pour éviter une guerre entre les dieux.  Sur sa route, il devra affronter une horde d\'ennemis mythologiques bien décidés à l\'empêcher d\'atteindre son but."
        ,"Daddario_Alexandra"
        ,"Aventure,Fantastique,Familial"
        ,"Z2UAv81ow40");
    }

    public void ajout299(){
        ajouter("Baywatch : Alerte à Malibu"
        ,"2017-05-25"
        ,"Le légendaire sauveteur Mitch Buchannon est contraint de s’associer à une nouvelle recrue, Matt Brody, aussi ambitieux que tête brûlée ! Ensemble, ils vont tenter de déjouer un complot criminel qui menace l\'avenir de la Baie…"
        ,"Daddario_Alexandra"
        ,"Comédie,Action,Crime"
        ,"-AtAyd4wNWg");
    }

    public void ajout300(){
        ajouter("Death Kiss"
        ,"2018-05-04"
        ,"Un homme au passé trouble arrive dans une ville infestée de criminels. Afin de protéger une jeune femme et sa fille, il va faire justice lui-même."
        ,"Bronzi_Robert"
        ,"Action,Thriller"
        ,"Ic5yBzPutRY");
    }

    public void ajout301(){
        ajouter("Escape from Death Block 13"
        ,"2021-11-02"
        ,""
        ,"Bronzi_Robert"
        ,"Action"
        ,"Eb90w40e3Mw");
    }

    public void ajout302(){
        ajouter("The Gardener"
        ,"2021-12-28"
        ,"Un immigrant vivant une vie tranquille comme jardinier dans un manoir en Angleterre doit s\'appuyer sur de vieilles compétences pour sauver la famille pour laquelle il travaille des envahisseurs domestiques."
        ,"Bronzi_Robert"
        ,"Action,Thriller"
        ,"aU4KmFs8X44");
    }

    public void ajout303(){
        ajouter("Harry Potter et les Reliques de la mort - 2ème partie"
        ,"2011-07-12"
        ,"Le combat entre les puissances du bien et du mal de l’univers des sorciers se transforme en guerre sans merci. L’école Poudlard est désormais sous la coupe du professeur Rogue et des force des ténèbres. Les enjeux sont devenus considérables et personne n’est plus en sécurité alors que se rapproche l’ultime épreuve de force avec Voldemort…"
        ,"Fiennes_Ralph"
        ,"Fantastique,Aventure"
        ,"aiaIfICU-Tk");
    }

    public void ajout304(){
        ajouter("GoldenEye"
        ,"1995-11-16"
        ,"Dans les derniers jours de la guerre froide, James Bond et son collègue et ami Alec Trevelyan s’introduisent dans l’usine soviétique de gaz neurotoxique Arkangel, afin de la détruire. Les deux hommes sont découverts et, au cours d’une violente bagarre, Trevelyan est fait prisonnier et exécuté sous les yeux de Bond par le général soviétique Ourumov. Bond s’enfuit de façon spectaculaire. Il restera obsédé par son échec à sauver son ami. 9 ans plus tard, alors que l’Union soviétique est devenue une constellation de nations indépendantes, James Bond rencontre Xenia Onatopp, une superbe créature qui joue au chat et à la souris avec lui. La partie se révèle pleine de charme mais aussi pleine de dangers."
        ,"Brosnan_Pierce"
        ,"Aventure,Action,Thriller"
        ,"L8s4NHVI_AI");
    }

    public void ajout305(){
        ajouter("Meurs un autre jour"
        ,"2002-11-17"
        ,"Une opération secrète, menée en Corée du Nord par James Bond et deux de ses hommes, est compromise par un traître non identifié. S\'ensuit une course-poursuite en hovercraft au cours de laquelle le colonel nord-coréen Moon trouve la mort et son lieutenant, Zao, est grièvement blessé. James Bond est quant à lui capturé et jeté dans une prison militaire. Après quelques mois de détention, ce dernier est libéré à l\'occasion d\'un échange de prisonniers organisé par Falco, le directeur de la National Security Agency. Démis de ses fonctions, l\'ancien agent secret est décidé à retrouver Zao et à démasquer le traître qui a entraîné sa chute. Sa quête, riche en rebondissements, l\'amènera à faire la rencontre de la belle et mystérieuse Jinx et de Gustav Graves, un mégalomaniaque propriétaire d\'un somptueux palais de glace islandais et d\'une arme d\'une puissance insoupçonnée."
        ,"Brosnan_Pierce"
        ,"Aventure,Action,Thriller"
        ,"BLPlB7wisp0");
    }

    public void ajout306(){
        ajouter("Mamma Mia!"
        ,"2008-07-03"
        ,"C\'est en 1999, sur la ravissante île grecque de Kalokairi que l\'aventure romantique commence, dans un hôtel méditerranéen isolé, la villa Donna, tenu par Donna, sa fille Sophie et le fiancé de Sophie, Sky. Juste à temps pour son mariage prochain, Sophie poste nerveusement trois invitations destinées à trois hommes bien différents dont elle pense que l\'un d\'eux est son père. De trois points du globe, trois hommes s\'apprêtent à retourner sur l\'île - et vers la femme - qui les avait enchantés 20 ans auparavant."
        ,"Brosnan_Pierce"
        ,"Comédie,Romance"
        ,"T04-138j3YQ");
    }

    public void ajout307(){
        ajouter("10 Cloverfield Lane"
        ,"2016-03-10"
        ,"Une jeune femme se réveille dans une cave après un accident de voiture. Ne sachant pas comment elle a atterri dans cet endroit, elle pense tout d\'abord avoir été kidnappée. Son gardien tente de la rassurer en lui disant qu\'il lui a sauvé la vie après une attaque chimique d\'envergure. En l\'absence de certitude, elle décide de s\'échapper..."
        ,"Elizabeth_Mary"
        ,"Thriller,Science-Fiction,Drame,Horreur"
        ,"IlD47b4zrSQ");
    }

    public void ajout308(){
        ajouter("Scott Pilgrim"
        ,"2010-08-12"
        ,"Scott Pilgrim n’a jamais eu de problème à trouver une petite amie, mais s’en débarrasser s’avère plus compliqué. Entre celle qui lui a brisé le cœur – et qui est de retour en ville – et l’adolescente qui lui sert de distraction au moment où Ramona entre dans sa vie - en rollers - l’amour n’a jamais été chose facile. Il va cependant vite réaliser que le nouvel objet de son affection traîne les plus singulières casseroles jamais rencontrées : une infâme ligue d’ex qui contrôlent sa vie amoureuse et sont prêts à tout pour éliminer son nouveau prétendant. À mesure que Scott se rapproche de Ramona, il est confronté à une palette grandissante d’individus patibulaires qui peuplent le passé de sa dulcinée : du mesquin skateur à la rock star végétarienne en passant par une affreuse paire de jumeaux. Et s’il espère séduire l’amour de sa vie, il doit triompher de chacun d’eux avant que la partie soit bel et bien « over »."
        ,"Elizabeth_Mary"
        ,"Action,Comédie,Romance"
        ,"lcb4vOb84ng");
    }

    public void ajout309(){
        ajouter("Destination Finale 3"
        ,"2006-02-09"
        ,"Pour fêter la fin de l\'année scolaire, Wendy et ses amis ont décidé de se retrouver dans un parc d\'attractions. La soirée s\'annonce comme la plus fun de l\'année. Pourtant, au moment d\'embarquer dans un immense roller coaster, Wendy a un terrible pressentiment. Alors que tous les autres se moquent d\'elle, elle quitte l\'attraction avec Kevin.Quelques instants plus tard, horrifiée, la jeune fille voit les wagons lancés à toute allure sortir des rails à une hauteur vertigineuse, tuant ses amis. Elle et quelques autres viennent de manquer le rendez-vous que leur avait fixé la mort. Ils vont découvrir que ce n\'est pas forcément une chance.Peu de temps après, le destin rattrape brutalement l\'un des survivants. Wendy comprend que, sur les photos qu\'elle a prises lors de cette tragique soirée, certains indices semblent désigner les prochaines victimes et ce qui les attend..."
        ,"Elizabeth_Mary"
        ,"Horreur,Mystère"
        ,"iqVxVKL-yOM");
    }

    public void ajout310(){
        ajouter("Magnum Force"
        ,"1973-12-13"
        ,"De nombreux assassinats sont commis à San Francisco. Les victimes : des proxénètes, des trafiquants de drogue ou des criminels. L\'inspecteur Harry est chargé de l\'affaire. Son supérieur Briggs le déteste. Harry va bientôt découvrir pourquoi."
        ,"Somers_Suzanne"
        ,"Drame,Crime,Action"
        ,"wtLoFPIACVw");
    }

    public void ajout311(){
        ajouter("Harry Potter à l\'école des sorciers"
        ,"2001-11-16"
        ,"Orphelin, Harry Potter a été élevé sans amour par son oncle et sa tante Dursley, béats devant leur exécrable fils, Dudley. Le jour de ses 11 ans, un brave colosse nommé Hagrid lui apprend qu\'il est un grand magicien. Comme l\'étaient ses parents, tués par le maléfique Voldemort. L\'heure est venue pour Harry de rejoindre la célèbre école de sorcellerie de Poudlard"
        ,"Radcliffe_Daniel"
        ,"Aventure,Fantastique"
        ,"P1BGgqhVGAI");
    }

    public void ajout312(){
        ajouter("Harry Potter et la Chambre des secrets"
        ,"2002-11-13"
        ,"Après de très mauvaises vacances passées chez ses odieux oncle et tante et son affreux cousin, Harry rejoint, non sans mal, l\'École des Sorciers où, avec ses amis Ron et Hermione et l\'aide ponctuelle de nouveaux venus, il parvient à élucider le mystère de la Chambre des secrets, malgré les pièges tendus par ses ennemis et sans le concours de Lockhart, un nouveau professeur fanfaron et lâche, chargé pourtant de vaincre les Forces du Mal."
        ,"Radcliffe_Daniel"
        ,"Aventure,Fantastique"
        ,"Z3T8PuWuoL0");
    }

    public void ajout313(){
        ajouter("Harry Potter et le Prisonnier d\'Azkaban"
        ,"2004-05-31"
        ,"Sirius Black, un dangereux sorcier criminel, s’échappe de la sombre prison d’Azkaban avec un seul et unique but : retrouver Harry Potter, en troisième année à l’école de Poudlard. Selon la légende, Black aurait jadis livré les parents du jeune sorcier à leur assassin, Lord Voldemort, et serait maintenant déterminé à tuer Harry…"
        ,"Radcliffe_Daniel"
        ,"Aventure,Fantastique"
        ,"CLncEeVf4ks");
    }

    public void ajout314(){
        ajouter("The Lobster"
        ,"2015-10-15"
        ,"Dans un futur proche, la société ne tolère plus les célibataires. Ceux-ci sont arrêtés et sommés de passer 45 jours maximum dans un hôtel. Là, entre différentes activités, ils doivent rencontrer un ou une élue qu\'ils épouseront par la suite. S\'ils n\'y parviennent pas avant le délai imposé, ils seront transformés en l\'animal qu\'ils ont choisi à leur arrivée. David, qui vient d\'intégrer l\'établissement, a choisi le homard comme animal de réincarnation. Il apprend difficilement les règles de l\'établissement. Puis, il fait la connaissance d\'un homme qui a un défaut d\'élocution et d\'un autre qui boite. Pour échapper à son destin, il s\'enfuit et rejoint dans les bois un groupe de résistants : les Solitaires..."
        ,"Farrell_Colin"
        ,"Comédie,Drame,Romance"
        ,"Sba-lPZeRJs");
    }

    public void ajout315(){
        ajouter("Total Recall: Mémoires programmées"
        ,"2012-08-02"
        ,"Modeste ouvrier, Douglas Quaid rêve de s\'évader de sa vie frustrante. L\'implantation de souvenirs que propose la société Rekall lui paraît l\'échappatoire idéale. S\'offrir des souvenirs d\'agent secret serait parfait... Mais lorsque la procédure d\'implantation tourne mal, Quaid se retrouve traqué par la police. Il ne peut plus faire confiance à personne, sauf peut-être à une inconnue qui travaille pour une mystérieuse résistance clandestine. Très vite, la frontière entre l\'imagination et la réalité se brouille. Qui est réellement Quaid, et quel est son destin ?"
        ,"Farrell_Colin"
        ,"Action,Science-Fiction,Thriller"
        ,"Q4nwzdq_2x4");
    }

    public void ajout316(){
        ajouter("Bons baisers de Bruges"
        ,"2008-02-08"
        ,"Après un contrat qui a mal tourné à Londres, deux tueurs à gages reçoivent l’ordre d’aller se faire oublier quelque temps à Bruges. Ray est rongé par son échec et déteste la ville, ses canaux, ses rues pavées et ses touristes. Ken, tout en gardant un œil paternaliste sur son jeune collègue, se laisse gagner par le calme et la beauté de la cité. Alors qu’ils attendent désespérément l’appel de leur employeur, leur séjour forcé les conduit à faire d’étranges rencontres avec des habitants, des touristes, un acteur américain nain tournant un film d’art et d\'essai européen, des prostituées et une jeune femme qui pourrait bien cacher quelques secrets aussi sombres que les leurs… Quand le patron finit par appeler et demande à l’un des tueurs d’abattre l’autre, les vacances se transforment en une course‐poursuite surréaliste dans les rues de la ville…"
        ,"Farrell_Colin"
        ,"Comédie,Drame,Crime"
        ,"rLSOx4ef9WY");
    }

    public void ajout317(){
        ajouter("Suburban Commando"
        ,"1991-10-04"
        ,"Un héros interstellaire d\'un monde lointain visite la terre et tente d\'adapter à une famille de banlieue encore genre banal."
        ,"Pearson_Malachi"
        ,"Action,Comédie,Familial,Science-Fiction"
        ,"CwwXrgvzIc4");
    }

    public void ajout318(){
        ajouter("Clueless"
        ,"1995-07-19"
        ,"Deux beaux mecs font irruption dans l\'univers de Cher : un nouveau camarade d\'école sexy et son ex demi-frère à l\'esprit carré mais bien mignon. Elle apprend maintenant que lorsqu\'il s\'agit d\'amour, elle est nulle."
        ,"Silverstone_Alicia"
        ,"Comédie,Romance"
        ,"Mgjwq1ZzdPQ");
    }

    public void ajout319(){
        ajouter("Batman & Robin"
        ,"1997-06-20"
        ,"Dans cette nouvelle aventure, Batman aura bien besoin de son audacieux partenaire Robin, juché sur sa fringante moto turbo. En effet, le glacial M. Frezze fait régner une vague de froid polaire sur Gotham City avec la complicité de la belle et vénéneuse Poison Ivy, au baiser mortel et aux formes sinueuses, qui rêve de soumettre le monde au pouvoir des femmes-fleurs."
        ,"Silverstone_Alicia"
        ,"Action,Science-Fiction,Aventure"
        ,"7KNzMj1njw8");
    }

    public void ajout320(){
        ajouter("Mise à mort du cerf sacré"
        ,"2017-10-20"
        ,"Un brillant chirurgien prend sous son aile un jeune adolescent. Dans un premier temps, ce dernier s’immisce au sein de cette famille et en perturbe progressivement le quotidien. Il devient de plus en plus inquiétant, menaçant. Une seule issue possible : un impensable sacrifice."
        ,"Silverstone_Alicia"
        ,"Drame,Thriller,Mystère"
        ,"qyTs0EuIwbI");
    }

    public void ajout321(){
        ajouter("Le Loup et le Lion"
        ,"2021-10-13"
        ,"À la mort de son grand père, Alma, jeune pianiste de 20 ans revient dans la maison de son enfance, perdue sur une île déserte du Canada. Là, tout bascule quand un louveteau et un lionceau en détresse surgissent dans sa vie. Elle choisit de les garder pour les sauver et l’improbable se produit : ils grandissent ensemble et s’aiment comme des frères. Mais leur monde idéal s’écroule lorsque leur secret est découvert..."
        ,"Kunz_Molly"
        ,"Familial,Aventure"
        ,"HZhkis2cNgo");
    }

    public void ajout322(){
        ajouter("Les Veuves"
        ,"2018-11-06"
        ,"Chicago, au cœur de la tourmente, quatre femmes, qui n\'ont rien en commun si ce n\'est une dette liée à l\'activité criminelle de leur mari décédé, prennent leur sort en main et conspirent ensemble pour forger l\'avenir qu\'elles se sont choisi."
        ,"Kunz_Molly"
        ,"Crime,Thriller"
        ,"kmev0UpnFF0");
    }

    public void ajout323(){
        ajouter("The Wise Kids"
        ,"2011-07-09"
        ,"Dans la petite ville conservatrice de Charleston en Caroline du Sud, trois adolescents s’apprêtent à franchir une nouvelle étape de la vie. Il y a Brea, fille de pasteur introvertie, sa meilleure amie Laura, et Tim, élevé par un père célibataire, et qui découvre son homosexualité. Entre drame et comédie, The Wise Kids est une chronique touchante sur les hésitations de l’adolescence, avec un jeune casting prometteur."
        ,"Kunz_Molly"
        ,"Drame"
        ,"O80zt9Ty_5U");
    }

    public void ajout327(){
        ajouter("Ant-Man et la Guêpe"
        ,"2018-07-04"
        ,"Après les événements survenus dans Captain America : Civil War, Scott Lang a bien du mal à concilier sa vie de super-héros et ses responsabilités de père. Mais ses réflexions sur les conséquences de ses choix tournent court lorsque Hope van Dyne et le Dr Hank Pym lui confient une nouvelle mission urgente... Scott va devoir renfiler son costume et apprendre à se battre aux côtés de La Guêpe afin de faire la lumière sur des secrets enfouis de longue date..."
        ,"Lilly_Evangeline,Rudd_Paul"
        ,"Action,Aventure,Science-Fiction"
        ,"A5jzpMR6rv4");
    }

    public void ajout328(){
        ajouter("Ant-Man"
        ,"2015-07-14"
        ,"L\'histoire d\'Ant-Man est celle d\'un petit escroc du nom de Scott Lang. Doté d\'une capacité étonnante - celle de rétrécir à volonté tout en démultipliant sa force - ce dernier doit embrasser la part de héros qui est en lui afin d\'aider son mentor, le docteur Hank Pym, à protéger d\'une nouvelle génération de redoutables menaces le secret du spectaculaire costume d\'Ant-Man. Contre des obstacles en apparence insurmontables, Pym et Lang, doivent mettre au point - et réussir - un audacieux cambriolage qui pourrait sauver le monde d\'une issue fatale…"
        ,"Lilly_Evangeline,Rudd_Paul"
        ,"Science-Fiction,Action,Aventure"
        ,"_mWjqYXA59E");
    }

    public void ajout329(){
        ajouter("Le Hobbit : La Bataille des cinq armées"
        ,"2014-12-10"
        ,"Atteignant enfin la Montagne Solitaire, Thorin et les Nains, aidés par Bilbon le Hobbit, ont réussi à récupérer leur royaume et leur trésor. Ils ont également réveillé le dragon Smaug qui déchaîne désormais sa colère sur les habitants de Lac-ville. À présent, les Nains, les Elfes, les Humains mais aussi les Wrags et les Orques menés par le Nécromancien, convoitent les richesses de la Montagne Solitaire."
        ,"Lilly_Evangeline"
        ,"Action,Aventure,Fantastique"
        ,"E5qN3zUF3Rw");
    }

    public void ajout330(){
        ajouter("Pirates des Caraïbes : La Fontaine de jouvence"
        ,"2011-05-15"
        ,"Dans cette histoire pleine d’action, où vérité, trahison, jeunesse éternelle et mort forment un cocktail explosif, le capitaine Jack Sparrow retrouve une femme qu’il a connu autrefois. Leurs liens sont‐ils faits d’amour ou, cette femme n’est‐elle qu’une aventurière sans scrupules qui cherche à l’utiliser pour découvrir la légendaire Fontaine de Jouvence ? Lorsqu’elle l’oblige à embarquer à bord du Queen Anne’s Revenge, le bateau du terrible pirate Barbe‐Noire, Jack ne sait plus ce qu’il doit craindre le plus : Le redoutable maître du bateau ou cette femme surgit de son passé…"
        ,"McShane_Ian"
        ,"Aventure,Action,Fantastique"
        ,"Kcqf4RqBaUI");
    }

    public void ajout331(){
        ajouter("Big Time Movie"
        ,"2012-03-10"
        ,"Carlos et Kendall vont enfin pouvoir réaliser leur rêve : faire une tournée avec leur groupe de rock préféré. Arrivés à Londres, les garçons se rendent compte que leur sac a été échangé contre un autre et sont alors entraînés dans une mystérieuse histoire d\'espionnage..."
        ,"Schmidt_Kendall"
        ,"Comédie"
        ,"DfEdI1qYgzQ");
    }

    public void ajout333(){
        ajouter("American Nightmare 3 : Élections"
        ,"2016-06-29"
        ,"Une sénatrice américaine se lance dans la course à l\'élection présidentielle en proposant l\'arrêt total de la Purge annuelle. Ses opposants profitent alors d\'une nouvelle édition de cette journée où tous les crimes sont permis pour la traquer et la tuer..."
        ,"Mitchell_Elizabeth"
        ,"Action,Horreur,Thriller"
        ,"MzHLBgYGqc0");
    }

    public void ajout334(){
        ajouter("Hyper Noël"
        ,"2002-10-31"
        ,"Scott Calvin exerce la profession de Père Noël depuis huit ans. Mais sa vie tourne au cauchemar lorsqu\'il apprend que s\'il ne se marie pas avant la fin du mois, il devra quitter son job. Parallèlement, il découvre que son fils Charlie figure sur sa liste annuelle des \"voyous\".Décidé à rencontrer le grand amour et à aider son garçon, Scott retourne chez lui, laissant un Père Noël adjoint mener les préparatifs de cette fin d\'année. Très vite, le remplaçant se met à prendre d\'étranges décisions..."
        ,"Mitchell_Elizabeth"
        ,"Fantastique,Comédie,Familial"
        ,"btfT48KUUKE");
    }

    public void ajout335(){
        ajouter("Jurassic World"
        ,"2015-06-06"
        ,"Après le décès de son fondateur John Hammond, la société de biotechnologie InGen a été rachetée par Simon Masrani, PDG de la Masrani Global Corporation. Deux décennies après les événements tragiques de 1993, un nouveau parc a ouvert ses portes sur Isla Nublar, au large du Costa Rica. C’est plus de vingt mille visiteurs qui débarquent chaque jour à « Jurassic World », le « plus grand parc à thèmes jamais construit dans l’histoire humaine » pour profiter pleinement du cadre idyllique de l’île et de ses attractions. Les scientifiques de la réserve biologique, sous la direction du Dr Henry Wu, étudient le comportement des animaux, des dinosaures vivants recréés à partir de leur ADN fossilisé dans de lʼambre. Mais alors que tout le monde croyait les leçons du passé assimilées, un nouvel incident éclate…"
        ,"Pratt_Chris"
        ,"Action,Aventure,Science-Fiction,Thriller"
        ,"wmzAfqhphq8");
    }

    public void ajout336(){
        ajouter("The Batman"
        ,"2022-03-01"
        ,"Durant les deux années passées à arpenter les rues en tant que Batman, Bruce Wayne s\'est aventuré au cœur des ténèbres de Gotham City. Disposant de seulement quelques alliés de confiance au sein du monde corrompu qu\'est l\'élite de la ville, le justicier solitaire s\'est imposé comme l\'unique incarnation de la vengeance parmi ses concitoyens. Lorsqu\'un meurtrier s\'en prend aux plus grandes personnalités de Gotham à l\'aide de machinations sadiques, le plus grand détective du monde se lance dans une enquête dans la pègre en suivant des indices mystérieux, croisant ainsi Selina Kyle, alias Catwoman, Oswald Cobblepot, alias le Pingouin, Carmine Falcone et Edward Nashton, alias le Riddler. Alors que les pistes et les plans du criminel s\'éclaircissent, Batman doit tisser de nouveaux liens, démasquer le coupable et rétablir la justice à Gotham City, depuis trop longtemps empoisonnée par l\'abus de pouvoir et la corruption."
        ,"Pattinson_Robert"
        ,"Crime,Mystère,Thriller"
        ,"hGQo1axtj60");
    }

    public void ajout337(){
        ajouter("Twilight, chapitre 5 : Révélation, 2e partie"
        ,"2012-11-13"
        ,"Après la naissance de sa fille Renésmée, Bella s’adapte peu à peu à sa nouvelle vie de vampire avec le soutien d’Edward. Se sentant menacés par cette naissance d’un nouveau genre, les Volturi déclarent la guerre à la famille Cullen. Pour préparer leur défense, les Cullen vont parcourir le monde pour rassembler les familles de vampires alliées et tenter de repousser les Volturi lors d’un ultime affrontement."
        ,"Pattinson_Robert"
        ,"Aventure,Fantastique,Drame,Romance"
        ,"CjdPAg-gyFE");
    }

    public void ajout338(){
        ajouter("L\'Effet papillon"
        ,"2004-01-17"
        ,"Une théorie prétend que si l\'on pouvait retourner dans le passé et changer quelques détails de notre vie, tout ce qui en découle serait modifié. On appelle cela «l\'effet papillon». Evan Treborn a cette faculté. Fasciné, il va d\'abord mettre ce don au service de ceux dont les vies ont été brisées dans leur enfance. Il peut enfin repartir dans le passé et sauver la seule jeune fille qu\'il ait jamais aimée. Mais Evan va découvrir que ce pouvoir est aussi puissant qu\'incontrôlable. Il va s\'apercevoir que s\'il change la moindre chose, il change tout. En intervenant sur le passé, il modifie le présent et se voit de plus en plus souvent obligé de réparer les effets indésirables de ses corrections..."
        ,"Smart_Amy"
        ,"Science-Fiction,Thriller"
        ,"VgkatOnVS_w");
    }

    public void ajout339(){
        ajouter("Hyper tension"
        ,"2006-08-31"
        ,"Chev Chelios est un tueur à gages qui n\'a pas rempli un banal contrat : la veille, il a en effet raté sa cible. Et ce matin-là, Chev est réveillé par un coup de téléphone de bien mauvaise augure. A l\'autre bout du fil, le malfrat Ricky Verona lui apprend qu\'il a été empoisonné dans son sommeil et qu\'il ne lui reste qu\'une heure à vivre… Désormais, Chelios ne doit surtout pas rester immobile - sous peine de mourir d\'un instant à l\'autre : il lui faut stimuler son adrénaline pour empêcher le poison de provoquer un arrêt cardiaque. Dans une terrible course contre la montre, Chev parcourt les rues de Los Angeles, n\'hésitant pas à affronter ceux qui osent se mettre en travers de sa route. Il n\'a dorénavant d\'autre choix que de trouver l\'antidote lui permettant d\'échapper à une mort certaine…"
        ,"Smart_Amy"
        ,"Action,Thriller,Crime"
        ,"bPsAMybWfXg");
    }

    public void ajout340(){
        ajouter("Hyper tension 2"
        ,"2009-04-16"
        ,"Chev Chelios affronte un parrain de la mafia chinoise qui lui a dérobé son cœur quasi indestructible et remplacé par une prothèse qu\'il doit stimuler en s\'envoyant des décharges électriques."
        ,"Smart_Amy"
        ,"Action,Thriller,Crime"
        ,"KvI4gYs1xME");
    }


    public void ajout343(){
        ajouter("Singam en campagne"
        ,"2021-09-10"
        ,"Un jeune politicien ayant trouvé un moyen sournois de gravir les échelons voit ses manœuvres malhonnêtes perturbées par une nouvelle présence inattendue."
        ,"Rabecca_Raichal"
        ,"Drame,Comédie"
        ,"IKNqeuxQKqk");
    }

    public void ajout344(){
        ajouter("Ant-Man et la Guêpe : Quantumania"
        ,"2023-02-15"
        ,"Tout va pour le mieux : Scott a écrit un livre à succès tandis que Hope défend avec le plus grand dévouement des causes humanitaires. Leur famille - Janet van Dyne et Hank Pym (les parents de Hope) et Cassie, la fille de Scott - font enfin partie de leur quotidien. Cassie partage la passion de sa nouvelle famille pour la science et la technologie, notamment en ce qui concerne le domaine quantique. Mais sa curiosité les entraîne tous dans une odyssée imprévue et sans retour dans le vaste monde subatomique, un endroit mystérieux où ils rencontrent d’étranges nouvelles créatures, une société en crise et un impitoyable maître du temps dont l’ombre menaçante commence tout juste à s’étendre. Scott et Cassie sont soudainement happés dans une direction tandis que Hope, Janet et Hank se retrouvent propulsés dans une autre. Tous se perdent dans un monde en guerre, sans savoir comment ils pourront en sortir ni même s\'ils retrouveront un jour le chemin de leur foyer..."
        ,"Rudd_Paul"
        ,"Action,Aventure,Science-Fiction"
        ,"Q0uaHT1wsTQ");
    }

    public void ajout345(){
        ajouter("Zootopie"
        ,"2016-02-11"
        ,"Avec ses quartiers comme la très chic Place du Sahara ou le glacial Toundraville, la métropole Zootopie, où ne vivent que des mammifères, est un melting pot où toutes les espèces cohabitent. Qu’on soit un énorme éléphant ou une minuscule musaraigne, tout le monde y a sa place. Mais quand l’optimiste agent Judy Hopps arrive en ville, elle découvre qu’être le premier lapin dans la police, au milieu d\'animaux costauds, n’est pas si simple. Pour faire ses preuves, elle décide de résoudre une enquête, même si pour cela, elle doit faire équipe avec Nick Wilde, un renard beau parleur et roi de l’arnaque. Certaines séquences ou certains motifs lumineux clignotants sont susceptibles d\'affecter les téléspectateurs photosensibles."
        ,"Bateman_Jason"
        ,"Animation,Aventure,Familial,Comédie"
        ,"RTZlzdHuIQQ");
    }

    public void ajout346(){
        ajouter("Comment tuer son boss ?"
        ,"2011-07-08"
        ,"Pour Nick, Kurt et Dale, la seule chose qui pourrait rendre le travail quotidien plus tolérable serait de réduire en poussière leurs intolérables patrons. Démissionner étant exclu, les trois copains échafaudent, avec l’aide de quelques verres de trop et les conseils douteux d’un ancien détenu, un plan quelque peu alambiqué, mais infaillible, pour se débarrasser de leurs employeurs respectifs... définitivement. Il n’y a qu’un problème : les plans les plus infaillibles ne le sont qu’autant que les cerveaux qui les ont conçus le sont."
        ,"Bateman_Jason"
        ,"Comédie,Crime"
        ,"3OtSpeIUyEk");
    }

    public void ajout347(){
        ajouter("Game Night"
        ,"2018-02-15"
        ,"Pour pimenter leur vie de couple, Max et Annie animent un jeu une nuit par semaine. Cette fois ils comptent sur Brooks, le frère charismatique de Max, pour organiser une super soirée à thème autour du polar, avec vrais faux malfrats et agents fédéraux ! Brooks a même prévu de se faire enlever… sauf qu\'il reste introuvable. En tentant de résoudre l\'énigme, nos joueurs invétérés commencent à comprendre qu\'ils se sont peut-être trompés sur toute la ligne. De fausse piste en rebondissement, ils n\'ont plus aucun point de repère et ne savent plus s\'il s\'agit encore d\'un jeu… ou pas. Cette nuit risque bien d\'être la plus délirante – et la plus dangereuse – de toute leur carrière de joueurs…"
        ,"Bateman_Jason"
        ,"Mystère,Comédie,Crime"
        ,"7Tm2XWl5FA4");
    }


    public void ajout350(){
        ajouter("Les Infiltrés"
        ,"2006-10-04"
        ,"À Boston, une lutte sans merci oppose la police à la pègre irlandaise. Pour mettre fin au règne du parrain Frank Costello, la police infiltre son gang avec « un bleu » issu des bas quartiers, Billy Costigan. Tandis que Billy s’efforce de gagner la confiance du malfrat vieillissant, Colin Sullivan entre dans la police au sein de l’Unité des Enquêtes Spéciales, chargée d’éliminer Costello. Mais Colin fonctionne en « sous‑marin » et informe Costello des opérations qui se trament contre lui. Risquant à tout moment d’être démasqués, Billy et Colin sont contraints de mener une double vie qui leur fait perdre leurs repères et leur identité. Traquenards et contre‑offensives s’enchaînent jusqu’au jour où chaque camp réalise qu’il héberge une taupe. Une course contre la montre s’engage entre les deux hommes avec un seul objectif : découvrir l’identité de l’autre sous peine d’y laisser sa peau…"
        ,"Scorsese_Martin"
        ,"Drame,Thriller,Crime"
        ,"btA3UIniGts");
    }

    public void ajout351(){
        ajouter("Ted"
        ,"2012-06-29"
        ,"À 8 ans, le petit John Bennett fit le voeu que son ours en peluche de Noël s’anime et devienne son meilleur ami pour la vie, et il vit son voeu exaucé. Presque 30 ans plus tard, l’histoire n’a plus vraiment les allures d’un conte de Noël. L’omniprésence de Ted aux côtés de John pèse lourdement sur sa relation amoureuse avec Lori. Bien que patiente, Lori voit en cette amitié exclusive, consistant principalement à boire des bières et fumer de l’herbe devant des programmes télé plus ringards les uns que les autres, un handicap pour John qui le confine à l’enfance, l’empêche de réussir professionnellement et de réellement s’investir dans leur couple. Déchiré entre son amour pour Lori et sa loyauté envers Ted, John lutte pour devenir enfin un homme, un vrai !"
        ,"Wahlberg_Mark,Kunis_Mila"
        ,"Comédie,Fantastique"
        ,"imAYGQ28kok");
    }

    public void ajout352(){
        ajouter("Transformers : L’Âge de l’extinction"
        ,"2014-06-25"
        ,"Alors que l’humanité panse ses plaies, après les événements de Transformers : La Face cachée de la Lune, les Autobots et les Decepticons ont disparu de la surface de la Terre. Mais un groupe formé de financiers et scientifiques puissants et ingénieux étudient les invasions successives des Transformers, afin de repousser les limites de la technologie au‐delà de ce qu’ils peuvent contrôler. Et pendant ce temps, une menace Transformer ancienne et puissante prend la Terre pour cible."
        ,"Wahlberg_Mark"
        ,"Science-Fiction,Action,Aventure"
        ,"ebsZ15sJ35E");
    }

    public void ajout353(){
        ajouter("Ted 2"
        ,"2015-06-25"
        ,"Nouveaux mariés, Ted et Tami‐Lynn essayent d’avoir un bébé et demandent à John d’être le donneur en vue d’une insémination artificielle. Cependant, s’il veut avoir la garde de l’enfant, Ted va devoir prouver devant un tribunal qu’il est véritablement humain."
        ,"Wahlberg_Mark"
        ,"Comédie,Fantastique"
        ,"S3AVcCggRnU");
    }

    public void ajout354(){
        ajouter("La Chute de la Maison-Blanche"
        ,"2013-03-20"
        ,"Mike Banning, ancien garde du corps du président des États-Unis, s’occupe désormais des basses besognes des services secrets. Lorsqu’un commando nord-coréen lance une attaque sur la Maison-Blanche, prenant en otage le président américain et son fils, il se retrouve seul à pouvoir leur venir en aide. Deux ans après avoir été tenu responsable de la mort accidentelle de la Première Dame, il va pouvoir faire preuve de sa loyauté et de sa bravoure."
        ,"Butler_Gerard"
        ,"Action,Thriller"
        ,"0mbqavEiL1c");
    }

    public void ajout355(){
        ajouter("Dragons"
        ,"2010-03-18"
        ,"L’histoire d’Harold, jeune Viking peu à son aise dans sa tribu où combattre les dragons est le sport national. Sa vie va être bouleversée par sa rencontre avec un dragon qui va peu à peu amener Harold et les siens à voir le monde d’un point de vue totalement différent."
        ,"Butler_Gerard"
        ,"Fantastique,Aventure,Animation,Familial"
        ,"8rR_zgI-cmk");
    }

    public void ajout356(){
        ajouter("Les Gardiens de la Galaxie : Volume 3"
        ,"2023-05-03"
        ,"Peter Quill, encore sous le choc d\'une terrible perte, doit rallier son équipe et se lancer dans un mission risquée et palpitante pour défendre l\'univers et protéger Rocket. Pendant ce temps, une nouvelle force imprévisible menace de faire tomber les Gardiens pour de bon."
        ,"Klementieff_Pom"
        ,"Science-Fiction,Aventure,Action"
        ,"WxA-eZ72FsQ");
    }

    public void ajout358(){
        ajouter("Call Me by Your Name"
        ,"2017-09-01"
        ,"Été 1983. Elio Perlman, 17 ans, passe ses vacances dans la villa du XVIIe siècle que possède sa famille en Italie, à jouer de la musique classique, à lire et à flirter avec son amie Marzia. Son père, éminent professeur, et sa mère, traductrice, lui ont donné une excellente éducation. Un jour, Oliver, un séduisant Américain qui prépare son doctorat, vient travailler auprès du père d\'Elio. Elio et Oliver vont bientôt découvrir l\'éveil du désir..."
        ,"Chalamet_Timothée,Hammer_Armie"
        ,"Romance,Drame"
        ,"-pkhSA1YF40");
    }

    public void ajout359(){
        ajouter("Le Roi"
        ,"2019-10-11"
        ,"Hal, jeune prince rebelle, tourne le dos à la royauté pour vivre auprès du peuple. Mais à la mort de son père, le tyrannique Henri IV d\'Angleterre, Hal ne peut plus échapper au destin qu\'il tentait de fuir et est couronné roi à son tour. Le jeune Henri V doit désormais affronter le désordre politique et la guerre que son père a laissés derrière lui, mais aussi le passé qui resurgit, notamment sa relation avec son ami et mentor John Falstaff, un chevalier alcoolique."
        ,"Chalamet_Timothée"
        ,"Drame,Histoire,Guerre"
        ,"bVH7LurFHHc");
    }

    public void ajout360(){
        ajouter("The Chaser"
        ,"2008-02-14"
        ,"Joong-ho, ancien flic devenu proxénète, reprend du service lorsqu\'il se rend compte que ses filles disparaissent les unes après les autres. Très vite, il réalise qu\'elles avaient toutes rencontré le même client, identifié par les derniers chiffres de son numéro de portable. Joong-ho se lance alors dans une chasse à l\'homme, persuadé qu\'il peut encore sauver Mi-jin, la dernière victime du tueur."
        ,"Sun-young_Kim"
        ,"Crime,Thriller,Action"
        ,"fYDDHY25Q18");
    }

    public void ajout363(){
        ajouter("Sherlock Holmes"
        ,"2009-12-23"
        ,"Aucune énigme ne résiste longtemps à Sherlock Holmes… Flanqué de son fidèle ami le Docteur John Watson, l’intrépide et légendaire détective traque sans relâche les criminels de tous poils. Ses armes : un sens aigu de l’observation et de la déduction, une érudition et une curiosité tous azimuts ; accessoirement, une droite redoutable… Mais une menace sans précédent plane aujourd’hui sur Londres – et c’est exactement le genre de défi dont notre homme a besoin pour ne pas sombrer dans l’ennui et la mélancolie. Après qu’une série de meurtres rituels a ensanglanté Londres, Holmes et Watson réussissent à intercepter le coupable : Lord Blackwood. À l’approche de son exécution, ce sinistre adepte de la magie noire annonce qu’il reviendra du royaume des morts pour exercer la plus terrible des vengeances."
        ,"Reilly_Kelly"
        ,"Action,Aventure,Crime,Mystère"
        ,"xm2B8zce_pg");
    }

    public void ajout364(){
        ajouter("Eden Lake"
        ,"2008-09-12"
        ,"Jenny est maîtresse d\'école. Son petit ami et elle quittent Londres pour passer un week-end romantique au bord d\'un lac. La tranquillité du lieu est perturbée par une bande d\'adolescents bruyants et agressifs qui s\'installent avec leur Rottweiler juste à côté d\'eux. A bout de nerfs, ces derniers leur demandent de baisser le son de leur radio. Grosse erreur !"
        ,"Reilly_Kelly"
        ,"Horreur,Thriller"
        ,"Yb2F9nPMe7o");
    }

    public void ajout365(){
        ajouter("127 heures"
        ,"2010-11-12"
        ,"Le 26 avril 2003, Aron Ralston, jeune homme de vingt‐sept ans, se met en route pour une randonnée dans les gorges de l’Utah. Il est seul et n’a prévenu personne de son excursion. Alpiniste expérimenté, il collectionne les plus beaux sommets de la région. Pourtant, au fin fond d’un canyon reculé, l’impensable survient: Au‐dessus de lui un rocher se détache et emprisonne son bras dans le mur de rocaille. Le voilà pris au piège, menacé de déshydratation et d’hypothermie, en proie à des hallucinations… Il parle à son ex petite amie, sa famille, et se demande si les deux filles qu’il a rencontrées dans le canyon juste avant son accident seront les dernières. Cinq jours plus tard, comprenant que les secours n’arriveront pas, il va devoir prendre la plus grave décision de son existence…"
        ,"Mara_Kate"
        ,"Aventure,Drame,Thriller"
        ,"AHkDZGvUrQc");
    }

    public void ajout366(){
        ajouter("Les 4 Fantastiques"
        ,"2015-08-05"
        ,"Quatre jeunes marginaux se téléportent dans un univers alternatif et dangereux qui modifie leur forme physique de façon choquante. Après que leurs vies aient été irrémédiablement changées, la fine équipe doit apprendre à maîtriser ses nouvelles capacités et à travailler de concert pour sauver la Terre d’un ancien allié devenu leur némésis."
        ,"Mara_Kate"
        ,"Action,Aventure,Science-Fiction"
        ,"f7KtZ-8NAoU");
    }

    public void ajout367(){
        ajouter("La Chapelle du Diable"
        ,"2021-03-31"
        ,"Suite à l\'apparition de la Vierge Marie dans une petite ville de la Nouvelle-Angleterre, une jeune fille sourde se met à entendre, parler et à guérir les infirmes. Ce phénomène surnaturel attire de nombreuses personnes souhaitant assister aux miracles, dont un journaliste en disgrâce qui y voit le moyen de relancer sa carrière. Tandis qu\'il mène l\'enquête, de terribles événements se produisent, l\'incitant à remettre en question l\'origine de ce prodige : relève-t-il du divin ou d\'une source bien plus sombre ?"
        ,"Dean_Jeffrey"
        ,"Horreur,Mystère"
        ,"kHk7QeA8Xtw");
    }

    public void ajout368(){
        ajouter("The Losers"
        ,"2010-04-23"
        ,"Un commando des Forces Spéciales est envoyé en Bolivie pour arrêter un grand trafiquant de drogue. Max, le chef de l\'opération, les envoie, en réalité, dans un piège: il les abandonne sur place et retourne aux États-Unis où il compte vendre une puissante arme à des terroristes. Aidé par la mystérieuse Aisha, le groupe parvient à rentrer en Amérique où ils tentent de faire échouer le dangereux projet de Max..."
        ,"Dean_Jeffrey"
        ,"Action,Aventure,Crime,Mystère,Thriller"
        ,"QSEM-hJF_vM");
    }

    public void ajout370(){
        ajouter("Nos Pires Voisins"
        ,"2014-05-08"
        ,"Un jeune couple avec un bébé se voit contraint de faire face suite aux difficultés rencontrées avec leurs voisins, membres d\'une maison de fraternité"
        ,"Byrne_Rose"
        ,"Comédie"
        ,"k8kcz8Hoq6A");
    }

    public void ajout371(){
        ajouter("Insidious"
        ,"2011-03-31"
        ,"Alors qu\'un jeune couple s\'installe dans sa nouvelle maison, l\'aîné de leurs enfants tombe dans un coma inexpliqué. Peu après, des phénomènes paranormaux jamais vus viennent hanter leurs nuits..."
        ,"Byrne_Rose"
        ,"Horreur,Thriller"
        ,"HZedg27LSZ8");
    }

    public void ajout372(){
        ajouter("X-Men : Le Commencement"
        ,"2011-06-01"
        ,"\"X-Men : Le Commencement\" nous entraîne aux origines de la saga X-Men, révélant une histoire secrète autour des événements majeurs du XXe siècle. Avant que les mutants n’aient révélé leur existence au monde, et avant que Charles Xavier et Erik Lehnsherr ne deviennent le Professeur X et Magneto, ils n’étaient encore que deux jeunes hommes découvrant leurs pouvoirs pour la première fois. Avant de devenir les pires ennemis, ils étaient encore amis, travaillaient avec d’autres mutants pour empêcher la destruction du monde, l’Armageddon. Au cours de cette opération, le conflit naissant entre les deux hommes s’accentua, et la guerre éternelle entre la Confrérie de Magneto et les X-Men du Professeur X éclata…"
        ,"Byrne_Rose,Bacon_Kevin"
        ,"Action,Science-Fiction,Aventure"
        ,"tT8oQT8ILpY");
    }

    public void ajout373(){
        ajouter("Le dernier jour de ma vie"
        ,"2017-03-02"
        ,"Une élève populaire du secondaire se rend compte qu\'elle est peut-être en train de revivre sans cesse le dernier jour de sa vie, jusqu\'à ce qu\'elle fasse les choses bien. Elle décide de changer des moments de ce jour afin d\'aider quelqu\'un d\'autre -- une jeune fille harcelée et déprimée."
        ,"Deutch_Zoey"
        ,"Drame,Mystère,Thriller,Fantastique"
        ,"sBq5s69S6sQ");
    }

    public void ajout374(){
        ajouter("Petits coups montés"
        ,"2018-06-15"
        ,"Deux assistants surmenés et sous-payés élaborent un plan pour se débarrasser de leurs effroyables chefs en les faisant craquer l\'un pour l\'autre."
        ,"Deutch_Zoey"
        ,"Romance,Comédie"
        ,"PqSjAOPBekQ");
    }

    public void ajout376(){
        ajouter("La Nonne"
        ,"2018-09-05"
        ,"Quand on apprend le suicide d\'une jeune nonne dans une abbaye roumaine, la stupéfaction est totale dans l\'Église catholique. Le Vatican missionne aussitôt un prêtre au passé trouble et une novice pour mener l\'enquête. Risquant leur vie, les deux ecclésiastiques doivent affronter une force maléfique – la nonne démoniaque de « Conjuring 2 » – qui bouscule leur foi et menace de détruire leur âme."
        ,"Farmiga_Taissa"
        ,"Horreur,Mystère,Thriller"
        ,"hxRjOBRINu0");
    }

    public void ajout378(){
        ajouter("La Nonne : La Malédiction de Sainte-Lucie"
        ,"2023-09-06"
        ,"En France, en 1956, un prêtre est assassiné dans un internat. Après la mort du prêtre, il se passe des choses qui ne peuvent plus être expliquées rationnellement. Sœur Irène est une nouvelle fois confrontée à un pouvoir démoniaque. Elle se rend vite compte que c\'est le démon Valak qui est de retour et qui se prépare à ses méfaits meurtriers."
        ,"Farmiga_Taissa"
        ,"Horreur,Mystère,Thriller"
        ,"IIwtiqye86w");
    }

    public void ajout379(){
        ajouter("Batman Begins"
        ,"2005-06-10"
        ,"Alors que le souvenir de ses parents assassinés le hante, Bruce Wayne, désemparé, erre à travers le monde cherchant les moyens de combattre l\'injustice et ses propres peurs. Avec l\'aide de son dévoué majordome et homme de confiance Alfred, de l\'inspecteur Jim Gordon et de son allié Lucius Fox, Wayne revient à Gotham City et révèle son alter ego: Batman, un justicier masqué qui utilise sa force, son intelligence et une batterie d\'armes high-tech pour combattre les forces sinistres qui menacent la ville…"
        ,"Bale_Christian"
        ,"Action,Crime,Drame"
        ,"jXrFsn9pcZY");
    }

    public void ajout380(){
        ajouter("Mariage à la Grecque 3"
        ,"2023-09-07"
        ,"Retrouvez la famille Portokalos alors qu’ils se rendent à une réunion de famille en Grèce. Au programme, un voyage chaleureux et hilarant, plein d’amour et de rebondissements. Opa!"
        ,"Nur_Stephanie"
        ,"Romance,Comédie"
        ,"bDXtgG-Cw_4");
    }

    public void ajout381(){
        ajouter("Irréversible"
        ,"2002-05-22"
        ,"Une jeune femme, Alex, se fait violer par un inconnu dans un tunnel. Son compagnon Marcus et son ex-petit ami Pierre décident de faire justice eux-mêmes."
        ,"Bellucci_Monica"
        ,"Drame,Thriller,Crime"
        ,"ZoDtJjqkfxU");
    }

    public void ajout382(){
        ajouter("Malèna"
        ,"2000-03-16"
        ,"Au printemps 1940, Mussolini a déclaré la guerre à la France et à la Grande‐Bretagne. La ville sicilienne de Castelcuto est en liesse. Renato Amoroso, un garçon de treize ans, est heureux pour d’autres raisons. Il vient de recevoir sa première bicyclette et de tomber sous le charme de Malena, une ravissante veuve de guerre qui fait tourner la tête à tous les hommes du village et attise la haine des épouses jalouses. Renato, littéralement envoûté, la suit partout avec son vélo. Mais Malena est victime des refoulements et des convoitises des habitants de Castelcuto. Le petit garçon va trouver le moyen de l’aider, et grâce à elle, il apprendra les leçons de la vie."
        ,"Bellucci_Monica"
        ,"Drame"
        ,"SxqUoUvNBXY");
    }

    public void ajout383(){
        ajouter("Spectre"
        ,"2015-10-26"
        ,"Un message cryptique surgi du passé entraîne James Bond dans une mission très personnelle à Mexico puis à Rome, où il rencontre Lucia Sciarra, la très belle veuve d’un célèbre criminel. Bond réussit à infiltrer une réunion secrète révélant une redoutable organisation baptisée Spectre. Pendant ce temps, à Londres, Max Denbigh, le nouveau directeur du Centre pour la Sécurité Nationale, remet en cause les actions de Bond et l’existence même du MI6, dirigé par M. Bond persuade Moneypenny et Q de l’aider secrètement à localiser Madeleine Swann, la fille de son vieil ennemi, Mr White, qui pourrait détenir le moyen de détruire Spectre. Fille de tueur, Madeleine comprend Bond mieux que personne… En s’approchant du cœur de Spectre, Bond va découvrir qu’il existe peut-être un terrible lien entre lui et le mystérieux ennemi qu’il traque…"
        ,"Bellucci_Monica"
        ,"Action,Aventure,Thriller"
        ,"DqVVeRHU68E");
    }

    public void ajout384(){
        ajouter("Aquaman"
        ,"2018-12-07"
        ,"Personnage légendaire depuis 70 ans, Aquaman est le Roi des Sept Mers, régnant à contrecœur sur Atlantis. Pris en étau entre les Terriens qui détruisent constamment la mer et les habitants d\'Atlantis prêts à se révolter, Aquaman doit protéger la planète tout entière…"
        ,"Momoa_Jason"
        ,"Action,Aventure,Fantastique"
        ,"ivvOKs7B-Vw");
    }

    public void ajout385(){
        ajouter("Zack Snyder\'s Justice League"
        ,"2021-03-18"
        ,"Bruce Wayne est déterminé à faire en sorte que le sacrifice ultime de Superman ne soit pas vain. Pour cela, avec l\'aide de Diana Prince, il met en place un plan pour recruter une équipe de métahumains afin de protéger le monde d\'une menace apocalyptique imminente. La tâche s\'avère plus difficile qu\'il ne l\'imaginait, car chacune des recrues doit faire face aux démons de son passé, et les surpasser, pour se rassembler et former une ligue de héros sans précédent. Désormais unis, Batman, Wonder Woman, Aquaman, Cyborg et Flash réussiront-ils à sauver la planète de Steppenwolf, DeSaad, Darkseid et de leurs terribles intentions ?"
        ,"Momoa_Jason"
        ,"Action,Aventure,Fantastique"
        ,"lTnRSMfFvu4");
    }

    public void ajout386(){
        ajouter("Headhunters"
        ,"2011-08-26"
        ,"En Norvège, Roger Brown a la réputation d\'être l\'un des meilleurs chasseurs de têtes sur le marché. Il affiche un train de vie luxueux, financé par une activité qu\'il dissimule soigneusement à ses collègues : cambrioleur. Roger s\'est fait une spécialité de dérober des toiles de maître, en prenant soin de les remplacer par des copies, afin de retarder le moment où leur propriétaire s\'aperçoit du méfait. Cela lui permet d\'entretenir sa femme, Diana, propriétaire d\'une galerie d\'art, et sa maîtresse, Lotte. Mais, un jour, Diana lui présente le riche chef d\'entreprise Clas Greve, un homme aussi riche que dangereux. Roger s\'intéresse à ses tableaux..."
        ,"Macody_Synnøve"
        ,"Thriller,Crime,Action"
        ,"fpOF6ZsFPxs");
    }

    public void ajout387(){
        ajouter("Millénium : Ce qui ne me tue pas"
        ,"2018-10-25"
        ,"Frans Balder, éminent chercheur suédois en intelligence artificielle fait appel à Lisbeth Salander afin de récupérer un logiciel qu\'il a créé et permettant de prendre le contrôle d\'armes nucléaires. Mais la NSA ainsi qu\'un groupe de terroristes mené par Jan Holster sont également sur la piste du logiciel. Traquée, Lisbeth va faire appel à son ami le journaliste Mikael Blomkvist qu\'elle n\'a pas vu depuis 3 ans."
        ,"Macody_Synnøve"
        ,"Thriller"
        ,"-BCejyylikI");
    }

    public void ajout388(){
        ajouter("2 Fast 2 Furious"
        ,"2003-06-05"
        ,"Brian O\'Conner a signé sa plus belle action, mais aussi sa faute la plus grave, en laissant filer le chef du gang de voleurs de voitures qu\'il avait mission d\'infiltrer. Radié de la police de Los Angeles, ce jeune flic rebelle, fan de vitesse et de rodéos, a gardé intact son honneur mais a gâché une belle carrière. Après 2 ans de galère, Brian O\'Conner se retrouve à Miami et se voit offrir une ultime chance de se racheter. Le FBI et les douanes locales surveillent depuis plusieurs mois le puissant homme d\'affaires Carter Verone, qu\'ils soupçonnent de se livrer à des opérations de blanchiment d\'argent. Mais leurs efforts sont restés vains, le seul indice dont ils disposent pour appâter et démasquer l\'énigmatique criminel étant sa passion pour les rodéos. Le temps presse, Brian semble être le seul espoir…"
        ,"Hauser_Cole"
        ,"Action,Crime,Thriller"
        ,"obWAvRgKFeg");
    }

    public void ajout389(){
        ajouter("Pitch Black"
        ,"2000-02-18"
        ,"Un vaisseau spatial transportant une quarantaine de civils est percuté par une météorite et se crashe sur une planète inconnue. Les membres de l’équipage périssent dans l’accident, à l’exception de Fry, une jeune pilote, et de quelques survivants. Parmi eux, un imam et ses disciples, un antiquaire, une géologue, une adolescente, le chasseur de Johns et Riddick, un criminel endurci en cours de transfert vers sa prison. Alors que le petit groupe tente de s’organiser sous un climat aride de jour perpétuel dominé par trois soleils, ils découvrent qu’une éclipse va bientôt frapper la planète, permettant à de monstrueuses créatures nocturnes de se mettre en chasse…"
        ,"Hauser_Cole"
        ,"Thriller,Science-Fiction,Action"
        ,"aWlFTd58Nuo");
    }

    public void ajout390(){
        ajouter("Man of Steel"
        ,"2013-06-12"
        ,"Un petit garçon découvre qu’il possède des pouvoirs surnaturels et qu’il n’est pas né sur Terre. Plus tard, il s’engage dans un périple afin de comprendre d’où il vient et pourquoi il a été envoyé sur notre planète. Mais il devra devenir un héros s’il veut sauver le monde de la destruction totale et incarner l’espoir pour toute l’humanité."
        ,"Cavill_Henry"
        ,"Action,Aventure,Science-Fiction"
        ,"Xw4VeR7tCxU");
    }

    public void ajout391(){
        ajouter("Justice League"
        ,"2017-11-15"
        ,"Après avoir retrouvé foi en l\'humanité, Bruce Wayne, inspiré par l\'altruisme de Superman, sollicite l\'aide de sa nouvelle alliée, Diana Prince, pour affronter un ennemi plus redoutable que jamais. Ensemble, Batman et Wonder Woman ne tardent pas à recruter une équipe de méta-humains pour faire face à cette menace inédite. Pourtant, malgré la force que représente cette ligue de héros sans précédent – Batman, Wonder Woman, Aquaman, Cyborg et The Flash –, il est peut-être déjà trop tard pour sauver la planète d\'une attaque apocalyptique…"
        ,"Cavill_Henry"
        ,"Action,Aventure,Science-Fiction"
        ,"cvUFoxU_Lq0");
    }

    public void ajout398(){
        ajouter("Sleepers"
        ,"1996-10-18"
        ,"Au milieu des années soixante, quatre gamins du quartier populaire de Hell\'s Kitchen de New York sont incarcérés dans une maison de redressement à la suite d\'une plaisanterie qui tourne au drame. Onze ans plus tard deux d\'entre eux, marqués à vie, retrouvent le gardien sadique et tortionnaire qui les a martyrisés lors de leur détention et l\'abattent froidement. Les deux autres, devenus respectivement journaliste et substitut du procureur, fidèles à la loi de Hell\'s Kitchen, vont tout tenter, avec le soutien du curé du quartier et du parrain de la mafia, pour les sortir de là."
        ,"Bacon_Kevin"
        ,"Crime,Drame,Thriller"
        ,"f6SeUzdUxC4");
    }

    public void ajout399(){
        ajouter("Tremors"
        ,"1990-01-19"
        ,"Perfection, un village perdu dans le désert de Nevada. Val et Earl, deux amis inséparables, rencontrent Rhonda LeBeck, une jeune sismologue, qui effectue un stage dans la région. Rhonda prétend que ses appareils ont enregistré des variations inexplicables. Quelque temps plus tard, Val et Earl retrouvent le corps sans vie de Fred, un éleveur de moutons, au sommet d\'un pylône. Les bêtes, quant à elles, ont été sauvagement éventrées. Les événements tragiques se succèdent. Plusieurs habitants sont attaqués par de mystérieuses créatures. Tout semble indiquer que celles-ci préparent une attaque d\'envergure. Les survivants se réfugient dans une ville voisine. Là, ils découvrent un morceau de ver de terre géant accroché sous leur voiture..."
        ,"Bacon_Kevin"
        ,"Horreur,Action,Science-Fiction"
        ,"nDUcTnswBpU");
    }

    public void ajout400(){
        ajouter("Le Monde fantastique d’Oz"
        ,"2013-03-07"
        ,"Lorsque Oscar Diggs, un petit magicien de cirque sans envergure à la moralité douteuse, est emporté à bord de sa montgolfière depuis le Kansas poussiéreux jusqu’à l’extravagant Pays d’Oz, il y voit la chance de sa vie. Tout semble tellement possible dans cet endroit stupéfiant composé de paysages luxuriants, de peuples étonnants et de créatures singulières ! Même la fortune et la gloire ! Celles-ci semblent d’autant plus simples à acquérir qu’il peut facilement se faire passer pour le grand magicien dont tout le monde espère la venue. Seules trois sorcières, Theodora, Evanora et Glinda semblent réellement douter de ses compétences…  Grâce à ses talents d’illusionniste, à son ingéniosité et à une touche de sorcellerie, Oscar va très vite se retrouver impliqué malgré lui dans les problèmes qu’affrontent Oz et ses habitants. Qui sait désormais si un destin hors du commun ne l’attend pas au bout de la route ?"
        ,"Spencer_Abigail"
        ,"Fantastique,Aventure,Familial"
        ,"2Hsq8qq0jdo");
    }

    public void ajout401(){
        ajouter("Cowboys & Envahisseurs"
        ,"2011-07-29"
        ,"Arizona, 1873. Un homme qui a perdu tout souvenir de son passé se retrouve à Absolution, petite ville austère perdue en plein désert. Le seul indice relatif à son histoire est un mystérieux bracelet qui enserre son poignet. Alors que la ville est sous l’emprise du terrible colonel Dolarhyde, les habitants d’Absolution vont être confrontés à une menace bien plus inquiétante, venue d’ailleurs…"
        ,"Spencer_Abigail"
        ,"Action,Science-Fiction,Thriller,Western"
        ,"U-dh6m37DvY");
    }

    public void ajout402(){
        ajouter("Agents très spéciaux : Code U.N.C.L.E."
        ,"2015-08-13"
        ,"Situé au début des années 60, en pleine guerre froide, The Man From U.N.C.L.E. s’attache au parcours de l’agent de la CIA Solo et à l’agent du KGB Kuryakin. Contraints de laisser de côté leur antagonisme ancestral, les deux hommes s’engagent dans une mission conjointe : mettre hors d’état de nuire une organisation criminelle internationale déterminée à ébranler le fragile équilibre mondial, en favorisant la prolifération des armes et de la technologie nucléaires. Pour l’heure, Solo et Kuryakin n’ont qu’une piste : le contact de la fille d’un scientifique allemand porté disparu, le seul à même d’infiltrer l’organisation criminelle. Ils se lancent dans une course contre la montre pour retrouver sa trace et empêcher un cataclysme planétaire."
        ,"Hammer_Armie"
        ,"Comédie,Action,Aventure"
        ,"1GKL57sPdQE");
    }

    public void ajout403(){
        ajouter("The Social Network"
        ,"2010-10-01"
        ,"Un soir d\'hiver 2003, Mark Zuckerberg, étudiant à Harvard et expert en informatique, s\'installe devant son ordinateur et se met à travailler avec frénésie sur une nouvelle idée, autour du développement de programme et de blogs. Ce qui prend forme dans cette chambre ce soir-là deviendra très vite un réseau communautaire à échelle mondiale et une révolution dans la communication. Seulement six années et quelques 500 millions d\'amis plus tard, Mark Zuckerberg est devenu le plus jeune milliardaire de l\'histoire… Cependant, pour cet entrepreneur, la réussite amène à la fois des complications personnelles et légales."
        ,"Hammer_Armie"
        ,"Drame"
        ,"lJwHm-_iojk");
    }

    @GetMapping(path = "/initvideos")
    public String initvideos(){
        videoRepository.deleteAll();
        acteurRepository.deleteAll();
        genreRepository.deleteAll();
        ajout0();
        ajout1();
        ajout2();
        ajout6();
        ajout8();
        ajout9();
        ajout10();
        ajout11();
        ajout12();
        ajout14();
        ajout15();
        ajout16();
        ajout17();
        ajout18();
        ajout19();
        ajout20();
        ajout21();
        ajout26();
        ajout27();
        ajout28();
        ajout29();
        ajout30();
        ajout31();
        ajout32();
        ajout34();
        ajout35();
        ajout36();
        ajout37();
        ajout38();
        ajout39();
        ajout40();
        ajout41();
        ajout42();
        ajout43();
        ajout44();
        ajout45();
        ajout46();
        ajout47();
        ajout48();
        ajout49();
        ajout50();
        ajout51();
        ajout52();
        ajout53();
        ajout54();
        ajout55();
        ajout56();
        ajout57();
        ajout58();
        ajout59();
        ajout60();
        ajout62();
        ajout63();
        ajout64();
        ajout65();
        ajout66();
        ajout67();
        ajout68();
        ajout69();
        ajout70();
        ajout71();
        ajout72();
        ajout73();
        ajout74();
        ajout75();
        ajout76();
        ajout79();
        ajout80();
        ajout81();
        ajout82();
        ajout83();
        ajout84();
        ajout85();
        ajout86();
        ajout87();
        ajout88();
        ajout89();
        ajout90();
        ajout95();
        ajout96();
        ajout97();
        ajout98();
        ajout99();
        ajout100();
        ajout101();
        ajout102();
        ajout103();
        ajout104();
        ajout105();
        ajout106();
        ajout107();
        ajout108();
        ajout109();
        ajout111();
        ajout112();
        ajout113();
        ajout114();
        ajout115();
        ajout116();
        ajout117();
        ajout118();
        ajout119();
        ajout120();
        ajout121();
        ajout122();
        ajout123();
        ajout124();
        ajout125();
        ajout126();
        ajout127();
        ajout128();
        ajout129();
        ajout130();
        ajout131();
        ajout132();
        ajout136();
        ajout137();
        ajout138();
        ajout139();
        ajout140();
        ajout141();
        ajout142();
        ajout143();
        ajout144();
        ajout145();
        ajout146();
        ajout147();
        ajout148();
        ajout149();
        ajout150();
        ajout151();
        ajout152();
        ajout153();
        ajout154();
        ajout155();
        ajout156();
        ajout157();
        ajout158();
        ajout159();
        ajout160();
        ajout161();
        ajout162();
        ajout163();
        ajout164();
        ajout165();
        ajout166();
        ajout167();
        ajout168();
        ajout169();
        ajout170();
        ajout172();
        ajout173();
        ajout177();
        ajout178();
        ajout179();
        ajout180();
        ajout182();
        ajout183();
        ajout184();
        ajout185();
        ajout189();
        ajout190();
        ajout191();
        ajout192();
        ajout193();
        ajout194();
        ajout196();
        ajout197();
        ajout198();
        ajout199();
        ajout200();
        ajout201();
        ajout202();
        ajout203();
        ajout204();
        ajout205();
        ajout206();
        ajout207();
        ajout209();
        ajout211();
        ajout212();
        ajout213();
        ajout214();
        ajout215();
        ajout216();
        ajout217();
        ajout218();
        ajout219();
        ajout220();
        ajout221();
        ajout222();
        ajout223();
        ajout224();
        ajout225();
        ajout226();
        ajout227();
        ajout228();
        ajout230();
        ajout231();
        ajout232();
        ajout233();
        ajout234();
        ajout235();
        ajout236();
        ajout237();
        ajout238();
        ajout239();
        ajout240();
        ajout241();
        ajout242();
        ajout243();
        ajout244();
        ajout245();
        ajout246();
        ajout247();
        ajout248();
        ajout249();
        ajout250();
        ajout251();
        ajout252();
        ajout253();
        ajout254();
        ajout255();
        ajout256();
        ajout257();
        ajout258();
        ajout259();
        ajout263();
        ajout265();
        ajout266();
        ajout267();
        ajout268();
        ajout269();
        ajout270();
        ajout271();
        ajout272();
        ajout273();
        ajout274();
        ajout275();
        ajout276();
        ajout277();
        ajout278();
        ajout279();
        ajout280();
        ajout281();
        ajout282();
        ajout283();
        ajout284();
        ajout285();
        ajout286();
        ajout287();
        ajout288();
        ajout289();
        ajout290();
        ajout291();
        ajout292();
        ajout293();
        ajout294();
        ajout295();
        ajout296();
        ajout297();
        ajout298();
        ajout299();
        ajout300();
        ajout301();
        ajout302();
        ajout303();
        ajout304();
        ajout305();
        ajout306();
        ajout307();
        ajout308();
        ajout309();
        ajout310();
        ajout311();
        ajout312();
        ajout313();
        ajout314();
        ajout315();
        ajout316();
        ajout317();
        ajout318();
        ajout319();
        ajout320();
        ajout321();
        ajout322();
        ajout323();
        ajout327();
        ajout328();
        ajout329();
        ajout330();
        ajout331();
        ajout333();
        ajout334();
        ajout335();
        ajout336();
        ajout337();
        ajout338();
        ajout339();
        ajout340();
        ajout343();
        ajout344();
        ajout345();
        ajout346();
        ajout347();
        ajout350();
        ajout351();
        ajout352();
        ajout353();
        ajout354();
        ajout355();
        ajout356();
        ajout358();
        ajout359();
        ajout360();
        ajout363();
        ajout364();
        ajout365();
        ajout366();
        ajout367();
        ajout368();
        ajout370();
        ajout371();
        ajout372();
        ajout373();
        ajout374();
        ajout376();
        ajout378();
        ajout379();
        ajout380();
        ajout381();
        ajout382();
        ajout383();
        ajout384();
        ajout385();
        ajout386();
        ajout387();
        ajout388();
        ajout389();
        ajout390();
        ajout391();
        ajout398();
        ajout399();
        ajout400();
        ajout401();
        ajout402();
        ajout403();
        return "404";
    }

}

