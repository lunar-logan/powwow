package io.powwow.xtemplate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FilesystemXTemplateLoaderImplTest {
    private static final Path TEST_RESOURCE_PATH = Paths.get(System.getProperty("user.dir")).resolve("src/test/resources");
    private XTemplateLoader xTemplateLoader;


    @Before
    public void setup() {
        xTemplateLoader = new FilesystemXTemplateLoaderImpl(TEST_RESOURCE_PATH.toString());
    }

    @Test
    public void load() throws IOException {
        Optional<String> maybeTemplate = xTemplateLoader.load("xtemplates/sample-json.ftlh");
        String expectedTemplateContent = Files.readString(TEST_RESOURCE_PATH.resolve("xtemplates/sample-json.ftlh"));

        Assert.assertTrue(maybeTemplate.isPresent());
        Assert.assertEquals(expectedTemplateContent, maybeTemplate.get());
    }

    @Test
    public void test_load_non_existent_template() {
        Optional<String> maybeTemplate = xTemplateLoader.load("xtemplates/some-random-non-existent-template.ftlh");
        Assert.assertTrue(maybeTemplate.isEmpty());
    }
}