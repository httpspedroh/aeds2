import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// ----------------------------------------------------------------------------------------------------------------- //

class Node {

    public boolean color;
    public Movie element;
    public Node left, right;

    public Node() { this(null); }
    public Node(Movie element) { this(element, false, null, null); }
    public Node(Movie element, boolean color) { this(element, color, null, null); }

    public Node(Movie element, boolean color, Node left, Node right) {

        this.color = color;
        this.element = element;
        this.left = left;
        this.right = right;
    }
}

class Alvinegra {

	private Node root;
    public int c_compares;
    public long t_begin;

	public Alvinegra() {

        // Initialize bench log
        c_compares = 0;
        t_begin = System.currentTimeMillis();

        // Initialize root
        root = null;
    }

	public boolean search(String x) {

        System.out.print("raiz");
        return search(x, root); 
    }

	private boolean search(String x, Node i) {
        
        boolean resp;
		
        if(i == null) {

            c_compares++;
            resp = false;
        }
        else if(x.compareTo(i.element.getOriginalTitle()) == 0) {
            
            c_compares += 2;
            resp = true;
        }
        else if(x.compareTo(i.element.getOriginalTitle()) < 0) {
            
            System.out.print(" esq");

            c_compares += 3;
            resp = search(x, i.left);
        }
        else {
            
            System.out.print(" dir");

            c_compares += 3;
            resp = search(x, i.right);
        }
        return resp;
	}

    public void insert(Movie element) throws Exception {
        
        if(root == null) {
            
            c_compares++;
            root = new Node(element);
        }
        else if(root.left == null && root.right == null) {

            c_compares += 4;

            if(element.getOriginalTitle().compareTo(root.element.getOriginalTitle()) < 0) root.left = new Node(element);
            else root.right = new Node(element);
        }
        else if(root.left == null) {

            c_compares += 5;

            if(element.getOriginalTitle().compareTo(root.element.getOriginalTitle()) < 0) {
                
                c_compares++;
                root.left = new Node(element);
            }
            else if(element.getOriginalTitle().compareTo(root.right.element.getOriginalTitle()) < 0) {
              
                c_compares += 2;

                root.left = new Node(root.element);
                root.element = element;
            } 
            else {

                c_compares += 2;

                root.left = new Node(root.element);
                root.element = root.right.element;
                root.right.element = element;
            }
            
            root.left.color = root.right.color = false;
        } 
        else if(root.right == null) {

            c_compares += 8;

            if(element.getOriginalTitle().compareTo(root.element.getOriginalTitle()) > 0) {
                
                c_compares++;
                root.right = new Node(element);
            }
            else if(element.getOriginalTitle().compareTo(root.left.element.getOriginalTitle()) > 0) {
                
                c_compares += 2;

                root.right = new Node(root.element);
                root.element = element;
            } 
            else {

                c_compares += 2;

                root.right = new Node(root.element);
                root.element = root.left.element;
                root.left.element = element;
            }

            root.left.color = root.right.color = false;
        } 
        else insert(element, null, null, null, root);

        root.color = false;
    }

    private void insert(Movie element, Node bisavo, Node avo, Node pai, Node i) throws Exception {
        
        if(i == null) {

            c_compares += 3;

            if(element.getOriginalTitle().compareTo(pai.element.getOriginalTitle()) < 0) i = pai.left = new Node(element, true);
            else i = pai.right = new Node(element, true);
        
            if(pai.color == true) balance(bisavo, avo, pai, i);
        } 
        else {
           
            c_compares += 5;

            if(i.left != null && i.right != null && i.left.color == true && i.right.color == true) {
              
                i.color = true;
                i.left.color = i.right.color = false;
              
                if(i == root) {
                    
                    c_compares++;
                    i.color = false;
                }
                else if(pai.color == true) {
                    
                    c_compares += 2;
                    balance(bisavo, avo, pai, i);
                }
            }
           
            if(element.getOriginalTitle().compareTo(i.element.getOriginalTitle()) < 0) {
                
                c_compares++;

                insert(element, avo, pai, i, i.left);
            }
            else if(element.getOriginalTitle().compareTo(i.element.getOriginalTitle()) > 0) {
                
                c_compares += 2;

                insert(element, avo, pai, i, i.right);
            }
            else {
                
                c_compares += 2;

                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    private void balance(Node bisavo, Node avo, Node pai, Node i) {
        
        c_compares++;
           
        if(pai.color == true) {

            c_compares += 2;

            if(pai.element.getOriginalTitle().compareTo(avo.element.getOriginalTitle()) > 0) {
               
                if(i.element.getOriginalTitle().compareTo(pai.element.getOriginalTitle()) > 0) avo = rotateLeft(avo);
                else avo = rotateRightLeft(avo);
            } 
            else {

                if(i.element.getOriginalTitle().compareTo(pai.element.getOriginalTitle()) < 0) avo = rotateRight(avo);
                else avo = rotateLeftRight(avo);
            }
           
            if(bisavo == null) {
                
                c_compares++;
                root = avo;
            }
            else if(avo.element.getOriginalTitle().compareTo(bisavo.element.getOriginalTitle()) < 0) {
                
                c_compares += 2;
                bisavo.left = avo;
            }
            else {
                
                c_compares += 2;
                bisavo.right = avo;
            }

            avo.color = false;
            avo.left.color = avo.right.color = true;
        }
    }

    private Node rotateRight(Node node) {
        
        Node leftNode = node.left;
        Node leftRightNode = leftNode.right;
  
        leftNode.right = node;
        node.left = leftRightNode;
  
        return leftNode;
    }
  
    private Node rotateLeft(Node node) {

        Node rightNode = node.right;
        Node rightLeftNode = rightNode.left;
  
        rightNode.left = node;
        node.right = rightLeftNode;
        return rightNode;
    }
  
    private Node rotateRightLeft(Node node) {

        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }
  
    private Node rotateLeftRight(Node node) {
        
        node.left = rotateLeft(node.left);
        return rotateRight(node);
     }
}

// ----------------------------------------------------------------------------------------------------------------- //

class Movie {

    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String name, original_title, genre, original_language, situation;
    private ArrayList<String> keywords;
    private Date release_date;
    private int duration;
    private float budget;

    public Movie() {

        this.name = "";
        this.original_title = "";
        this.genre = "";
        this.original_language = "";
        this.situation = "";
        this.keywords = new ArrayList<String>();
        this.release_date = new Date();
        this.duration = 0;
        this.budget = 0;
    }

    public Movie(String name, String original_title, String genre, String original_language, String situation, ArrayList<String> keywords, Date release_date, int duration, float budget) {

        this.name = name;
        this.original_title = original_title;
        this.genre = genre;
        this.original_language = original_language;
        this.situation = situation;
        this.keywords = keywords;
        this.release_date = release_date;
        this.duration = duration;
        this.budget = budget;
    }

    public void setName(String name) { this.name = name; }
    public void setOriginalTitle(String original_title) { this.original_title = original_title; }
    public void setgenre(String genre) { this.genre = genre; }
    public void setOriginalLanguage(String original_language) { this.original_language = original_language; }
    public void setSituation(String situation) { this.situation = situation; }
    public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }
    public void setReleaseDate(Date release_date) { this.release_date = release_date; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setBudget(float budget) { this.budget = budget; }

    public String getName() { return this.name; }
    public String getOriginalTitle() { return this.original_title; }
    public String getGenre() { return this.genre; }
    public String getOriginalLanguage() { return this.original_language; }
    public String getSituation() { return this.situation; }
    public ArrayList<String> getKeywords() { return this.keywords; }
    public Date getReleaseDate() { return this.release_date; }
    public int getDuration() { return this.duration; }
    public float getBudget() { return this.budget; }
    
    public Movie clone() {

        Movie cloned = new Movie();

        cloned.name = this.name;
        cloned.original_title = this.original_title;
        cloned.genre = this.genre;
        cloned.original_language = this.original_language;
        cloned.situation = this.situation;
        cloned.keywords = this.keywords;
        cloned.release_date = this.release_date;
        cloned.duration = this.duration;
        cloned.budget = this.budget;

        return cloned;
    }

    public void print() {

        MyIO.println(this.name + " " + this.original_title + " " + default_dateFormat.format(this.release_date) + " " + this.duration + " " + this.genre + " " + this.original_language + " " + this.situation + " " + this.budget + " " + this.keywords);
    }

    public void readHTML(String filename) {

        try {

            // Read HTML file
            String basefile = "/tmp/filmes/" + filename;

            FileInputStream fstream = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            // ------------------------------------ //

            // Create movie object
            String name = null, original_title = null, genre = null, original_language = null, situation = null;
            ArrayList<String> keywords = new ArrayList<String>();
            Date release_date = null;
            int duration = -1;
            float budget = -1;

            // ------------------------------------ //
        
            // Start to explode HMTL file
            String line;

            while((line = br.readLine()) != null) {

                // --------------------------- //

                // Find movie name
                if(name == null) {
                    
                    if(line.indexOf("<title>") != -1) {

                        name = removeTags(line.replace("&#8212;", "?"));
                        name = name.substring(4, name.length() - 35);
                    }
                }

                // --------------------------- //

                // Find movie original title
                if(original_title == null) {
                    
                    if(line.indexOf("<p class=\"wrap\"><strong>") != -1) {

                        original_title = removeTags(line);
                        original_title = original_title.substring(20, original_title.length());
                    }
                }

                // --------------------------- //

                // Find movie release date
                if(release_date == null) {
                    
                    if(line.indexOf("<span class=\"release\">") != -1) {

                        // Read util line cleaning it
                        line = onlyDigitsAndSym(br.readLine());

                        try { release_date = default_dateFormat.parse(line); } 
                        catch (java.text.ParseException e) { e.printStackTrace(); }
                    }
                }

                // --------------------------- //

                // Find movie duration
                if(duration == -1) {
                    
                    if(line.indexOf("<span class=\"runtime\">") != -1) {

                        br.readLine(); // Skip one line
                        line = br.readLine(); // Read util line

                        int h_pos = line.indexOf("h"),
                            m_pos = line.indexOf("m"),
                            hours = 0,
                            minutes = 0;

                        if(h_pos != -1) hours = Integer.parseInt(line.substring(firstDigit(line), h_pos));
                        if(m_pos != -1) minutes = Integer.parseInt(line.substring(firstDigit(line, h_pos == -1 ? 0 : h_pos), line.length() - 1));

                        duration = (hours * 60) + minutes;
                    }
                }

                // --------------------------- //

                // Find movie genres
                if(genre == null) {
                    
                    if(line.indexOf("<span class=\"genres\">") != -1) {

                        br.readLine(); // Skip one line
                        genre = removeTags(br.readLine()).substring(6); // Read util line cleaning it
                    }
                }

                // --------------------------- //

                // Find movie original language
                if(original_language == null) {
                    
                    if(line.indexOf("<bdi>Idioma original</bdi>") != -1) {

                        original_language = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                    }
                }

                // --------------------------- //

                // Find movie situation
                if(situation == null) {
                    
                    if(line.indexOf("<bdi>Situação</bdi>") != -1) {

                        situation = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                    }
                }

                // --------------------------- //

                // Find movie budget
                if(budget == -1) {
                    
                    if(line.indexOf("<bdi>Orçamento</bdi>") != -1) {

                        // Read util line cleaning it
                        line = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                        line = line.replace(",", "");
                        
                        if(line.charAt(0) == '-') budget = 0;
                        else budget = Float.parseFloat(line.substring(1));
                    }
                }

                // --------------------------- //

                // Find movie keywords
                if(keywords.size() == 0) {
                    
                    if(line.indexOf("<h4><bdi>Palavras-chave</bdi></h4>") != -1) {

                        // Skip two lines until keywords starts
                        for(int x = 0; x < 2; x++) line = br.readLine();

                        // Verify if keywords == 0
                        if(line.indexOf("<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>") == -1)
                        {
                            // Skip more two lines until keywords starts
                            for(int x = 0; x < 2; x++) line = br.readLine();

                            while(true) {

                                if(line.compareTo("    </ul>") == 0) break;

                                line = removeTags(line);

                                // Add to list only keywords
                                if(line.length() > 6) keywords.add(line.substring(8));

                                line = br.readLine();
                            }
                        }
                    }
                }
            }

            // ------------------------------------ //
            
            // Verify variables still "null"
            if(original_title == null) original_title = name;

            // Set movie object
            this.setName(name);
            this.setOriginalTitle(original_title);
            this.setgenre(genre);
            this.setOriginalLanguage(original_language);
            this.setSituation(situation);
            this.setKeywords(keywords);
            this.setReleaseDate(release_date);
            this.setDuration(duration);
            this.setBudget(budget);

            // ------------------------------------ //
        
            // Close HTML file
            fstream.close();
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    // -------------------------------------------------------------------------------------- //

    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String removeTags(String str) {

        String removed = "";
        
        for(int i = 0; i < str.length(); i++) {

            if(str.charAt(i) == '<') {

                i++;

                while(str.charAt(i) != '>') i++;
            } 
            else if(str.charAt(i) == '&') { 
                
                i++;

                if(str.charAt(i) != 'a') while(str.charAt(i) != ';') i++; // Ignore "amps"
                else {

                    removed += str.charAt(i - 1);
                    removed += str.charAt(i);
                }
            } 
            else removed += str.charAt(i);
        }
        return removed;
    }

    public static String onlyDigitsAndSym(String str) {

        String removed = "";

        for(int i = 0; i < str.length(); i++) {

            if((str.charAt(i) >= '0' && str.charAt(i) <= '9') || str.charAt(i) == ',' || str.charAt(i) == '/' || str.charAt(i) == '.') removed += str.charAt(i);
            else i++;
        }
        return removed;
    }

    public static int firstDigit(String str, int start) {
         
        for(int i = start; i != str.length(); i++) if(str.charAt(i) >= '0' && str.charAt(i) <= '9') return i;
        return -1;
    }

    public static int firstDigit(String str) { return firstDigit(str, 0); }


    // -------------------------------------------------------------------------------------- //

    public static void main(String[] args) throws Exception {

        MyIO.setCharset("utf-8");

        Scanner scr = new Scanner(System.in);
        String line = null;
        Alvinegra movies = new Alvinegra();
        
        // ------------------------------------------------------------------------------------------------------------ //
        
        // First part - Insert
        line = scr.nextLine().trim();

        while(true) {

            if(isFim(line)) break;
            
            // ------------------------------------ //
            
            // Create movie object
            Movie movie = new Movie();

            movie.readHTML(line);
            movies.insert(movie);

            // ------------------------------------ //
            
            line = scr.nextLine().trim();
        }

        // ------------------------------------------------------------------------------------------------------------ //

        // Second part - Search
        line = scr.nextLine().trim();

        while(true) {

            if(isFim(line)) break;
            
            // ------------------------------------ //
            
            System.out.println(line);
            System.out.print((movies.search(line) ? " SIM" : " NAO") + "\n");

            // ------------------------------------ //
            
            line = scr.nextLine().trim();
        }

        // ------------------------------------------------------------------------------------------------------------ //
        
        Arq.openWrite("753045_alvinegra.txt");
        Arq.println("753045\t" + (System.currentTimeMillis() - movies.t_begin) + "ms\t" + movies.c_compares);
        Arq.close();

        scr.close();
    }
}