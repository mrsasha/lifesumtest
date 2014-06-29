
package com.alterego.lifesumtest.app.data;

import com.alterego.lifesumtest.LifesumItem;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Response{
   	@Getter private List<LifesumItem> list;
}
