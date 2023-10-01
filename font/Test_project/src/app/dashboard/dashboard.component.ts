import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../service/api.service';
import { Employee } from '../service/Employee';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  email:any;
  employee!:Employee;

  constructor(private api: ApiService, private router :Router){


  }

  ngOnInit(): void {
    this.email = localStorage.getItem('email');
    if(this.email != null){
      this.getEmployeeDetails();
      this.router.navigate(['/dashboard']);
    }else{
      this.router.navigate(['/login']);
    }
  }

  getEmployeeDetails(){
    this.api.getRequest(`find-employee/${this.email}`).subscribe((res:any)=>{
      this.employee=res[0];
    })
  }

  empLogout(){
    localStorage.clear();
    this.router.navigate(['/login']);
  }


}
