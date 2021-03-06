package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MyController {

    private Map<Long, Photo> photos = new HashMap<>();

    @RequestMapping("/")
    public String onIndex(Model model) {
        model.addAttribute("photos", photos.values());
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile photo) {
        if (photo.isEmpty())
            throw new PhotoErrorException();

        try {
            long id = System.currentTimeMillis();
            photos.put(id, new Photo(id, photo.getOriginalFilename(), photo.getBytes()));
            return onIndex(model);
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping(value = "/viewphoto/{photo_id}", method = RequestMethod.GET)
    public String onViewPhoto(Model model, @PathVariable("photo_id") long id) {
        model.addAttribute("photo_id", id);
        return "photoback";
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(Model model, @PathVariable("photo_id") String strIds) {

        try {
            boolean er = false;
            String[] arStrId = strIds.split("[,]");
            for (String s : arStrId) {
                long l = Long.valueOf(s);
                if (photos.remove(l) == null) {
                    er = true;
                }
            }
            if (er) {
                throw new PhotoNotFoundException();
            } else {
                return onIndex(model);
            }
        } catch (Exception e) {
            throw new PhotoErrorException();
        }

    }

    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = photos.get(id).getBytes();
        if (bytes == null)
            throw new PhotoNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}
