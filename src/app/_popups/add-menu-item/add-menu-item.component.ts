import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from 'src/app/_services/api.service';

@Component({
  selector: 'app-add-menu-item',
  templateUrl: './add-menu-item.component.html',
  styleUrls: ['./add-menu-item.component.scss']
})
export class AddMenuItemComponent implements OnInit {

  addItemForm!: FormGroup;
  allCategories: any;
  actionBtn: string = 'Add';
  
  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private router: Router,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<AddMenuItemComponent>,
    @Inject(MAT_DIALOG_DATA) public editData: any
  ) {}
  
  ngOnInit(): void {
    this.addItemForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$'), Validators.minLength(3), Validators.maxLength(30)]],
      price: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      description: ['', [Validators.required]],
      category: ['', [Validators.required]],
    });

    if (this.editData) {
      console.log(this.editData, 'editdata');

      this.actionBtn = 'Update';
      this.addItemForm.controls['name'].setValue(this.editData.name);
      this.addItemForm.controls['price'].setValue(this.editData.price);
      this.addItemForm.controls['description'].setValue(this.editData.description);
      this.addItemForm.controls['category'].setValue(this.editData.category.id);
    }

    this.api.getAllCategries().subscribe((res) => {
      this.allCategories = res;
      console.log(this.allCategories);
    });
  }

  onSubmit() {
    if (!this.editData) {
      console.log(this.addItemForm.value);
      const formData = { ...this.addItemForm.value };
      delete formData.category;

      if (this.addItemForm.valid) {
        this.api.addFood(this.addItemForm.value.category, formData).subscribe(
          (res) => {
            this.toastr.success('Food Added Successfully', 'Success', {
              timeOut: 1000,
            });
            this.addItemForm.reset();
            this.dialogRef.close('save');
            // this.router.navigate(['menu-list'])
          },
          (error) => {
            if (error && error.status === 409) {
              this.toastr.warning('Food Already Exist', 'Warning', {
                timeOut: 1000,
              });
            }
          }
        );
      }
    } else{
      this.updateFood()
    }
  }

  updateFood(){
    const formData = { ...this.addItemForm.value };
    delete formData.category;
    console.log(formData, 'formData');
    console.log(this.editData.id,'this.editData.id')
    
    this.api.updateFood(this.editData.id, formData).subscribe(res => {
      this.toastr.success("Food Updated Successfully..!", "Success", { timeOut: 1000 });
      this.addItemForm.reset();
      this.dialogRef.close('update');
    },
    (error) => {
      if (error && error.status === 409) {
        this.toastr.error("Food Already Exist", "Warning", { timeOut: 1000 });
      } 
    })
  }

  onCancel() {
    this.addItemForm.reset();
    this.dialogRef.close();
  }

}
