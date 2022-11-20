package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println("=====> Boundary For In CaseSensitive <======");

	    HashMap hmCaseSen = new HashMap(true);

        System.out.println("== Menambahkan 'nama' ke dalam tree");
        hmCaseSen.insertNode("nama", "lorem ipsum");
        new TreePrinter(hmCaseSen.getRoot()).print(System.out);
        System.out.println();

        System.out.println("== Menambahkan 'umur' ke dalam tree");
        hmCaseSen.insertNode("umur", "19 tahun");
        new TreePrinter(hmCaseSen.getRoot()).print(System.out);
        System.out.println();

        System.out.println("== Menambahkan 'Nama' ke dalam tree");
        hmCaseSen.insertNode("Nama", "lorem ipsum color");
        new TreePrinter(hmCaseSen.getRoot()).print(System.out);
        System.out.println();


        System.out.println("\n\n\n\n=====> Boundary For Not CaseSensitive <======");


        HashMap hmNotCaseSen = new HashMap(false);

        System.out.println("== Menambahkan 'nama' ke dalam tree");
        hmNotCaseSen.insertNode("nama", "lorem ipsum");
        new TreePrinter(hmNotCaseSen.getRoot()).print(System.out);
        System.out.println();

        System.out.println("== Menambahkan 'umur' ke dalam tree");
        hmNotCaseSen.insertNode("umur", "19 tahun");
        new TreePrinter(hmNotCaseSen.getRoot()).print(System.out);
        System.out.println();

        System.out.println("== Menambahkan 'id' ke dalam tree");
        hmNotCaseSen.insertNode("id", "0123456789");
        new TreePrinter(hmNotCaseSen.getRoot()).print(System.out);
        System.out.println();

        System.out.println("== Menambahkan 'Nama' ke dalam tree");
        hmNotCaseSen.insertNode("Nama", "lorem ipsum dolor");
        new TreePrinter(hmNotCaseSen.getRoot()).print(System.out);
        System.out.println();


        System.out.println("== Menambahkan 'status' ke dalam tree");
        hmNotCaseSen.insertNode("status", "aktif");
        new TreePrinter(hmNotCaseSen.getRoot()).print(System.out);
        System.out.println();

    }
}
