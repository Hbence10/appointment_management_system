import { Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { OtherService } from '../../../services/other-service';
import { Details } from '../../../models/details.model';

@Component({
  selector: 'app-details-editor',
  imports: [MatButtonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule],
  templateUrl: './details-editor.html',
  styleUrl: './details-editor.scss',
})
export class DetailsEditor implements OnInit {
  private otherService = inject(OtherService)
  close = output()
  detailsForm!: FormGroup;
  openingDetailsForm!: FormGroup;
  dayNames = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]

  ngOnInit(): void {
    //Adatok lekérése

        this.detailsForm = new FormGroup({
          address: new FormControl(this.otherService.getDetailsForFooter.getAddress, [Validators.required]),
          phone: new FormControl(this.otherService.getDetailsForFooter.getPhone, [Validators.required]),
          email: new FormControl(this.otherService.getDetailsForFooter.getEmail, [Validators.required, Validators.email]),
          firePhone: new FormControl(this.otherService.getDetailsForFooter.getFirePhone, [Validators.required])
        })

    this.otherService.getOpeningDetails().subscribe({
      next: response => {

      },
      error: error => console.log(error)
    })
  }

  saveDetails() {
    this.otherService.updateDetails(new Details(1, this.detailsForm.controls["address"].value, this.detailsForm.controls["phone"].value, this.detailsForm.controls["email"].value, this.detailsForm.controls["firePhone"].value)).subscribe({
      next: response => console.log(response)
    })
  }
}
