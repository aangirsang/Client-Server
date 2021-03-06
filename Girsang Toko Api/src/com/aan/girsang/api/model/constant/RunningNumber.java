/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aan.girsang.api.model.constant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="RUNNINGNUMBER")
public class RunningNumber implements Serializable{

    @Id
    @Column(name="ID_RUNNINGNUMBER")
    private String id;

    @Column(name="NUMBER")
    private Integer number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
