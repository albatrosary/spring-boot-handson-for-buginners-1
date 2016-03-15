package megascus.spring.boot.handson.controller;

import megascus.spring.boot.handson.model.BookService;
import megascus.spring.boot.handson.model.Book;
import megascus.spring.boot.handson.model.BookJDBCCompornent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author megascus
 */
@Controller
@RequestMapping("books")
public class BookController {

    // ここから追加
    @Autowired
    BookJDBCCompornent bookjdbc;

    @RequestMapping(method = RequestMethod.GET, params = "title")
    String search(Model model, @RequestParam String title) {
        model.addAttribute("books", bookjdbc.searchByTitle(title));
        return "books/list";
    }
    // ここまで追加

    @Autowired
    BookService service;

    @ModelAttribute(value = "book") // 画面に表示したい初期データ bookという名前でthymeleafと共有する
    Book setUpForm() {
        return new Book();
    }

    @RequestMapping(method = RequestMethod.GET)
    String list(Model model) {
        model.addAttribute("books", service.findAll()); // booksという名前で一覧のデータをthymeleafと共有する
        return "books/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    String create() {
        return "books/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    String create(@Validated Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "books/create";
        }
        service.save(book);
        return "redirect:/books";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    String update(@RequestParam Long id, Model model) {
        model.addAttribute("book", service.findOne(id));
        return "books/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    String update(@RequestParam Long id, @Validated Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "update";
        }
        book.setId(id);
        service.update(book);
        return "redirect:/books";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/books";
    }

    @RequestMapping(value = {"update", "create"}, params = "goToTop", method = RequestMethod.POST)
    String gotoTop() {
        return "redirect:/books";
    }
}
