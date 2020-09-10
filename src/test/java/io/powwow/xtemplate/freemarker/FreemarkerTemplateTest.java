package io.powwow.xtemplate.freemarker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.powwow.xtemplate.FilesystemXTemplateLoaderImpl;
import io.powwow.xtemplate.XTemplate;
import io.powwow.xtemplate.XTemplateFactory;
import io.powwow.xtemplate.XTemplateLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FreemarkerTemplateTest {
    private static final Path TEST_RESOURCE_PATH = Paths.get(System.getProperty("user.dir")).resolve("src/test/resources");
    private XTemplateLoader xTemplateLoader;
    private XTemplateFactory xTemplateFactory;
    private final ObjectMapper MAPPER = new ObjectMapper();


    @Before
    public void setup() {
        xTemplateLoader = new FilesystemXTemplateLoaderImpl(TEST_RESOURCE_PATH.toString());
        xTemplateFactory = new FreemarkerTemplateFactory(xTemplateLoader);
    }

    @Test
    public void test_template() throws JsonProcessingException {
        XTemplate template = xTemplateFactory.getTemplateById("xtemplates/sample-json.ftlh");
        Assert.assertNotNull(template);

        StringWriter writer = new StringWriter();
        template.render(Map.of("name", "Maya", "id", 1), writer);
        Map<String, Object> result = MAPPER.readValue(writer.toString(), new TypeReference<>() {
        });

        Assert.assertTrue(result.containsKey("name"));
        Assert.assertEquals("Maya", result.get("name"));
        Assert.assertEquals(1, result.get("id"));
    }
}