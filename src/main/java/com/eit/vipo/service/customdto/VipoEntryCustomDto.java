package com.eit.vipo.service.customdto;

import java.util.*;

public class VipoEntryCustomDto {
    private String filename;
    private List<Prop> props;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String value) {
        this.filename = value;
    }

    public List<Prop> getProps() {
        return props;
    }

    public void setProps(List<Prop> value) {
        this.props = value;
    }
}
