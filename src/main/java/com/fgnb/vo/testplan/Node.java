package com.fgnb.vo.testplan;

import lombok.Data;

import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class Node {
    private String label;
    private List<Node> children;
}
