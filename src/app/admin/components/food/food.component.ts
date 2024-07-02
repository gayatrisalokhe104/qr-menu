import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AddCategoryComponent } from 'src/app/_popups/add-category/add-category.component';
import { AddMenuItemComponent } from 'src/app/_popups/add-menu-item/add-menu-item.component';
import { ApiService } from 'src/app/_services/api.service';
import { PromptService } from 'src/app/_services/prompt.service';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {

  allCategories: any;

  constructor(
    private dialog: MatDialog,
    private api: ApiService,
    private toastr: ToastrService,
    private router: Router,
    private prompt: PromptService,
  ) {}

  ngOnInit(): void {
    this.api.getAllCategries().subscribe((res) => {
      console.log('from mewnu added')
      this.allCategories = res;
      console.log(this.allCategories);
    });
  }

  openDialog() {
    this.dialog.open(AddMenuItemComponent, {
      width: '30%',
    }).afterClosed().subscribe(val => {
      if(val === 'save'){
        this.ngOnInit();
      }
    })
  }

  editFood(editData: any) {
    this.dialog.open(AddMenuItemComponent, {
      width: '30%',
      data: editData
    }).afterClosed().subscribe(val => {
      if(val === 'update'){
        this.ngOnInit();
      }
    })
  }

  openCategoryDialog() {
    this.dialog.open(AddCategoryComponent, {
      width: '30%',
    }).afterClosed().subscribe(val => {
      if(val === 'save'){
        this.ngOnInit();
      }
    })
  }

  editCategory(editData: any){
    this.dialog.open(AddCategoryComponent, {
      width: '30%',
      data: editData
    }).afterClosed().subscribe(val => {
      if(val === 'update'){
        this.ngOnInit();
      }
    })
  }

  deleteFood(id: number) {
    const data = {
      title : "Confirm Delete",
      text: "Do You Want To Delete Food ?"
    }
    this.prompt.openPrompt(data).subscribe(res => {
      if(res) {
        this.api.deleteFood(id).subscribe((res) => {
          this.toastr.success('Food Deleted Successfully...', 'Success', {timeOut:1000});
          this.ngOnInit();
        });
      }else {
        this.toastr.info("Deletion Cancelled", "Info", {timeOut:700})
      }
    })    
  }

  deleteCategory(id: number){
    const data = {
      title : "Confirm Delete",
      text: "Do You Want To Delete Category ?"
    }
    this.prompt.openPrompt(data).subscribe(res => {
      console.log(res);
      if(res){
        this.api.deleteCategory(id).subscribe(res => {
          this.toastr.success('Category Deleted Successfully...', 'Success', {timeOut:1000});
          this.ngOnInit();
        })
      }else {
        this.toastr.info("Deletion Cancelled", "Info", {timeOut:700})
      }
    }) 
  }

  updateAvailability(data: any) {
    this.api.updateCategory(data.id, data).subscribe((res) => {
      this.toastr.success('Food Availability Updated...', 'Success', {timeOut:1000});
    });
  }

}
