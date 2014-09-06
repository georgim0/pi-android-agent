package com.kupepia.piandroidagent.features.objects;

public class PackageDetails {
    private Boolean installStatus;
    private String version;
    private String packName;
    private String description;

    public PackageDetails( Boolean installStatus, String version,
            String packName, String description ) {
        this.installStatus = installStatus;
        this.version = version;
        this.packName = packName;
        this.description = description;
    }

    public Boolean getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus( Boolean installStatus ) {
        this.installStatus = installStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName( String packName ) {
        this.packName = packName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PackageDetails [installStatus=" + installStatus + ", version="
                + version + ", packName=" + packName + ", description="
                + description + "]";
    }

}
