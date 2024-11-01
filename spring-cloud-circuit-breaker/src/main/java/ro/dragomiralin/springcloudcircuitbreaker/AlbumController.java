package ro.dragomiralin.springcloudcircuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;
    private final PhotoService photoService;

    @GetMapping("/albums")
    public String albums(@RequestParam(required = false) boolean failure) {
        return albumService.getAlbumList(failure);
    }

    @GetMapping("/photos")
    public String photos() {
        return photoService.fetchPhotos();
    }

}
