package com.example.docker_mysql_springboot.services;

import com.example.docker_mysql_springboot.models.Item;
import com.example.docker_mysql_springboot.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // CREATE ou UPDATE
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // READ: liste complète
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // READ: un seul item
    public Item getItemById(Long id) {
        Optional<Item> optional = itemRepository.findById(id);
        return optional.orElse(null);
    }

    // DELETE
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}