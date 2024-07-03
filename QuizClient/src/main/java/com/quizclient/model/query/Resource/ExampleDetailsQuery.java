package com.quizclient.model.query.Resource;

import java.util.List;

public class ExampleDetailsQuery extends ExampleQuery {
    private List<FileQuery> files;

    public List<FileQuery> getFiles() {
        return files;
    }
}
