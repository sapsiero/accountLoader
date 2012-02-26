package com.sapsiero.accountLoader

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 4:25 PM
 *
 */
class ContentWriter {
    
    String folder

    public ContentWriter(String folder){
        this.folder = folder + (folder.endsWith("/") ? "" : "/")
    }

    public void ensureFolder() {
        File folder = new File(folder)
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }
    
    public void fetch(ContentProcessor processor) {
        processor.process { docType, docAttr, doc ->
            def extension
            switch (docType) {
                case Website.DocType.PDF:
                    extension = "pdf"
                    break
                case Website.DocType.XML:
                    extension = "xml"
                    break
                case Website.DocType.HTML:
                    extension = "html"
                    break
                default:
                    extension = "unknown"
                    break
            }
            //noinspection GroovyAssignabilityCheck
            save(docAttr.name, extension, doc)
        }
    }

    public void save(String name, String ext, InputStream inputStream) {
        File file = new File("${folder}${name}_${new Date().format("yyyyMMdd_HHmmssSSS")}.${ext}")
        file.withOutputStream { out ->
            def bis= new BufferedInputStream(inputStream)
            out << bis
        }
    }

    public void save(String name, String ext, String content) {
        File file = new File("${folder}${name}_${new Date().format("yyyyMMdd_HHmmssSSS")}.${ext}")
        file.withPrintWriter("UTF-8") { out ->
            def bis= new StringReader(content)
            out << bis
        }
    }
}

