import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { OrderDetailsComponent } from 'src/app/_popups/order-details/order-details.component';
import { ApiService } from 'src/app/_services/api.service';
import { PromptService } from 'src/app/_services/prompt.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  displayedColumns: string[] = ['orderId', 'customerName', 'table', 'amount', 'pay-status', 'date', 'action'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  orders: any;

  constructor(  
    private api: ApiService,
    private datePipe: DatePipe,
    private toastr: ToastrService,
    private dialog: MatDialog,
    private prompt: PromptService
  ){}
  
  ngOnInit(): void {
    this.api.getAllOrders().subscribe(res => {   
      this.orders = res
      this.dataSource = new MatTableDataSource(this.orders)
      console.log(this.orders,'datasorce');
      
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  openDialog(data: any) {
    this.dialog.open(OrderDetailsComponent, {
      width: '80%',
      data: data
    })
  }

  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    // If you want to filter specifically by orderId or customerName
    this.dataSource.filterPredicate = (data, filter) => {
      const orderIdMatch = data.orderId.toString().includes(filter);
      const customerNameMatch = data.customerName.toLowerCase().includes(filter);
      return orderIdMatch || customerNameMatch;
    };

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  deleteOrder(id: number){
    const data = {
      title: 'Confirm Delete',
      text: 'Do You Want To Delete Order ?',
    };
    this.prompt.openPrompt(data).subscribe((res) => {
      if (res) {
        this.api.deleteOrder(id).subscribe((res) => {
          this.toastr.success('Order Deleted Successfully...', 'Success', {
            timeOut: 1000,
          });
          this.ngOnInit();
        });
      } else {
        this.toastr.info('Deletion Cancelled', 'Info', { timeOut: 700 });
      }
    });
  }

  deleteAllOrders() {
    const data = {
      title: 'Confirm Delete',
      text: 'Do You Want To Delete All Orders?',
    };
    this.prompt.openPrompt(data).subscribe((res) => {
      if (res) {
        this.api.deleteAllOrders().subscribe((res) => {
          this.toastr.success('Orders Deleted Successfully...', 'Success', {
            timeOut: 1000,
          });
          this.ngOnInit();
        });
      } else {
        this.toastr.info('Deletion Cancelled', 'Info', { timeOut: 700 });
      }
    });
  }

  formatDateTime(localDateTime: number[]): string {
    const [year, month, day, hour, minute] = localDateTime;
    const date = new Date(year, month - 1, day, hour, minute);
    
    const options: Intl.DateTimeFormatOptions = {
      day: '2-digit',
      month: 'long',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false,
    };
    
    const formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);
    return formattedDate || '';
  }

}
