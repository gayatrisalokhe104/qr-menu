import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from 'src/app/_services/api.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit{

  addCategoryForm!: FormGroup
  actionBtn : string = "Add";

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<AddCategoryComponent>,
    @Inject (MAT_DIALOG_DATA) public editData: any,
  ){}

  ngOnInit(): void {
    this.addCategoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$'), Validators.minLength(3), Validators.maxLength(30)]]
    })

    if(this.editData){
      this.actionBtn = "Update"
      this.addCategoryForm.controls['name'].setValue(this.editData.name);
    }
  }

  onSubmit() {
    if(!this.editData){
      if(this.addCategoryForm.valid){
        this.api.addCategory(this.addCategoryForm.value).subscribe(res => {
            this.toastr.success("Category Added Successfully..!", "Success", { timeOut: 1000 });
            this.addCategoryForm.reset();
            this.dialogRef.close('save');
        },
        (error) => {
          if (error && error.status === 409) {
            this.toastr.error("Category Already Exist", "Warning", { timeOut: 1000 });
          } 
        })
      }
    }else{
      this.updateCategory()
    }
  }

  updateCategory(){
    this.api.updateCategory(this.editData.id, this.addCategoryForm.value).subscribe(res => {
      this.toastr.success("Category Updated Successfully..!", "Success", { timeOut: 1000 });
      this.addCategoryForm.reset();
      this.dialogRef.close('update');
    },
    (error) => {
      if (error && error.status === 409) {
        this.toastr.error("Category Already Exist", "Warning", { timeOut: 1000 });
      } 
    })
  }

  onCancel() {
    this.addCategoryForm.reset()
    this.dialogRef.close()
  }


}
