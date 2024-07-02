import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from 'src/app/_services/api.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  orderDetail: any;

  constructor(
    private api: ApiService,
    private dialogRef: MatDialogRef<OrderDetailsComponent>,
    @Inject (MAT_DIALOG_DATA) public editData: any,
    private toast: ToastrService,
  ){}

  ngOnInit(): void {
    this.orderDetail = this.editData
    console.log('orderId Data ', this.orderDetail);
  }
  
  onCancel() {
    this.dialogRef.close();
  }
  

}
