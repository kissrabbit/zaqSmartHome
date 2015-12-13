package com.zaq.smartHome.ws;

import java.util.WeakHashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaq.smartHome.db.Cmd;

@RestController  
@RequestMapping("/admin/command")
public class HttpCommand {
	private static WeakHashMap<String, Cmd> cmdCache=new WeakHashMap<>();
	
	@RequestMapping("/{id}")  
    public boolean exec(@PathVariable("id") Long id) {  
		System.out.println(id);
        return true;  
    }  
	
}
