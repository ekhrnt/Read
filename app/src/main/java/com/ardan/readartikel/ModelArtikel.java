package com.ardan.readartikel;

/**
 * Created by Ardan on 18/05/2017.
 */
public class ModelArtikel {

    private String id_artikel;
    private String judul;
    private String isi_artikel;
    private String tanggal_post;
    private String penulis;
    private String foto;

    public ModelArtikel()
    {

    }

    public ModelArtikel(String id_artikel, String judul, String isi_artikel, String tanggal_post,
                       String penulis, String foto)
    {
        super();
        this.id_artikel = id_artikel;
        this.judul = judul;
        this.isi_artikel = isi_artikel;
        this.tanggal_post = tanggal_post;
        this.penulis = penulis;
        this.foto = foto;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(String id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi_artikel() {
        return isi_artikel;
    }

    public void setIsi_artikel(String isi_artikel) {
        this.isi_artikel = isi_artikel;
    }

    public String getTanggal_post() {
        return tanggal_post;
    }

    public void setTanggal_post(String tanggal_post) {
        this.tanggal_post = tanggal_post;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
