package com.kupepia.piandroidagent.objects;

public class Rule {

    private String protocol;
    private String target;
    private String otherInfo;
    private String destination;
    private String source;
    private String option;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget( String target ) {
        this.target = target;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo( String otherInfo ) {
        this.otherInfo = otherInfo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination( String destination ) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource( String source ) {
        this.source = source;
    }

    public String getOption() {
        return option;
    }

    public void setOption( String option ) {
        this.option = option;
    }

    public Rule clone() {
        Rule r = new Rule();
        r.destination = destination;
        r.option = option;
        r.otherInfo = otherInfo;
        r.protocol = protocol;
        r.source = source;
        r.target = target;
        return r;
    }

}
