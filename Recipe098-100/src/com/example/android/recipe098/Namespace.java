package com.example.android.recipe098;

import java.util.Map;

import com.google.api.client.xml.XmlNamespaceDictionary;

public class Namespace {
    public static final XmlNamespaceDictionary DICTIONARY = new XmlNamespaceDictionary();
    static {
        Map<String, String> map = DICTIONARY.namespaceAliasToUriMap;
        map.put("", "http://www.w3.org/2005/Atom");
        map.put("app", "http://www.w3.org/2007/app");
        map.put("atom", "http://www.w3.org/2005/Atom");
        map.put("batch", "http://schemas.google.com/gdata/batch");
        map.put("docs", "http://schemas.google.com/docs/2007");
        map.put("gAcl", "http://schemas.google.com/acl/2007");
        map.put("gd", "http://schemas.google.com/g/2005");
        map.put("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
        map.put("xml", "http://www.w3.org/XML/1998/namespace");
    }
}
