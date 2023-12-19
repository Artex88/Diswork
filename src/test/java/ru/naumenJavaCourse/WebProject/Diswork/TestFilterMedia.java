package ru.naumenJavaCourse.WebProject.Diswork;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
import ru.naumenJavaCourse.WebProject.Diswork.services.StatusService;
import ru.naumenJavaCourse.WebProject.Diswork.services.TagService;
import ru.naumenJavaCourse.WebProject.Diswork.services.TypeService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestFilterMedia extends DisworkApplicationTests {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private  MediaService mediaService;

    @Autowired
    private  TagService tagService;

    @Autowired
    private  StatusService statusService;

    @Autowired
    private TypeService typeService;

    @Test
    public void testTypeFilterMedia(){
        Type type = typeService.findById(2);
        List<Media> media1 = mediaService.filterMedia(type, null, null, null, Collections.emptySet());
        List<Media> media2 = mediaRepository.findByType(type);
        assertEquals(media1.size(), media2.size());
    }

    @Test
    public void testStatusFilterMedia(){
        Status status = statusService.findById(3);
        List<Media> media1 = mediaService.filterMedia(null, status, null, null, Collections.emptySet());
        List<Media> media2 = mediaRepository.findByStatus(status);
        assertEquals(media1.size(), media2.size());
    }

    @Test
    public void testEpisodeDurationFilterMedia(){
        String episodeDuration = "средний";
        List<Media> media1 = mediaService.filterMedia(null, null, episodeDuration, null, Collections.emptySet());
        List<Media> media2 = mediaRepository.findByEpisodeDuration(episodeDuration);
        assertEquals(media1.size(), media2.size());
    }

    @Test
    public void testReleasePeriodFilterMedia(){
        String releasePeriod = "1990e годы";
        List<Media> media1 = mediaService.filterMedia(null, null, null, releasePeriod, Collections.emptySet());
        List<Media> media2 = mediaRepository.findByReleasePeriod(releasePeriod);
        assertEquals(media1.size(), media2.size());
    }

    @Test
    public void testNullFilterMedia(){
        List<Media> media2 = mediaService.filterMedia(null, null,null,null, Collections.emptySet());
        assertEquals(media2.size(), mediaRepository.findAll().size());
    }

    @Test
    public void testTagsFilterMedia(){
        Tag tag1 = tagService.findById(8);
        Tag tag2 = tagService.findById(9);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag1);
        tagSet.add(tag2);
        List<Media> media1 = mediaService.filterMedia(null, null, null, null, tagSet);
        List<Media> media2 = mediaRepository.findByTags(tagSet, tagSet.size());
        assertEquals(media1.size(), media2.size());
    }
}
