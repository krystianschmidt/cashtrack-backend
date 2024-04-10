package de.krystianschmidt.cashtrack.plugins.rest;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import de.krystianschmidt.cashtrack.adapter.category.CategoryToCategoryResourceMapper;
import de.krystianschmidt.cashtrack.adapter.category.CreateCategoryDto;
import de.krystianschmidt.cashtrack.adapter.category.CreateCategoryDtoToCategoryMapper;
import de.krystianschmidt.cashtrack.adapter.category.budget.BudgetDto;
import de.krystianschmidt.cashtrack.adapter.category.budget.BudgetDtoToBudgetMapper;
import de.krystianschmidt.cashtrack.adapter.category.budget.BudgetToBudgetResourceMapper;
import de.krystianschmidt.cashtrack.domain.category.Budget;
import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.category.CategoryApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryApplication categoryApplication;
    private final CreateCategoryDtoToCategoryMapper categoryDtoToCategoryMapper;
    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;

    private final BudgetDtoToBudgetMapper budgetDtoToBudgetMapper;
    private final BudgetToBudgetResourceMapper budgetToBudgetResourceMapper;

    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody CreateCategoryDto categoryDto){
        Category newCategory = categoryDtoToCategoryMapper.apply(categoryDto);
        categoryApplication.addCategory(newCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<CategoryResource>> getCategories(){
        List<Category> categories = categoryApplication.getCategories();

        return ResponseEntity.ok(
                categories.stream()
                .map(categoryToCategoryResourceMapper)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable("id") UUID categoryId){
        categoryApplication.removeCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/budget")
    ResponseEntity<?> createBudget(@PathVariable UUID id, @RequestBody BudgetDto budgetDto){
        Budget newBudget = budgetDtoToBudgetMapper.apply(budgetDto);
        categoryApplication.addBudget(id, newBudget);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/budget")
    ResponseEntity<?> deleteBudget(@PathVariable UUID id){
        categoryApplication.removeBudget(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
