package com.bancolc.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("banco")
public class BancoLcApiProperty {

  //private String origenPermitido =  "http://10.100.1.100:4200";
  private String origenPermitido =  "http://localhost:4200";

  private final Seguridad seguridad = new Seguridad();


  public Seguridad getSeguridad() {
    return seguridad;
  }

  public String getOrigenPermitido() {
    return origenPermitido;
  }

  public void setOrigenPermitido(String origenPermitido) {
    this.origenPermitido = origenPermitido;
  }

  public static class Seguridad {

    private boolean enableHttps;

    public boolean isEnableHttps() {
      return enableHttps;
    }

    public void setEnableHttps(boolean enableHttps) {
      this.enableHttps = enableHttps;
    }

  }

}
