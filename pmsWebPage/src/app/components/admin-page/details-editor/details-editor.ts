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
  otherService = inject(OtherService)
  close = output()
  detailsForm!: FormGroup;
  openingDetailsForm!: FormGroup;
  dayNames = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]

  ngOnInit(): void {
    this.detailsForm = new FormGroup({
      address: new FormControl(this.otherService.detailsForFooter.getAddress, [Validators.required]),
      phone: new FormControl(this.otherService.detailsForFooter.getPhone, [Validators.required]),
      email: new FormControl(this.otherService.detailsForFooter.getEmail, [Validators.required, Validators.email]),
      firePhone: new FormControl(this.otherService.detailsForFooter.getFirePhone, [Validators.required])
    })

    this.openingDetailsForm = new FormGroup({})
    this.otherService.openingDetails.forEach(openingDetails => {
      console.log(openingDetails)
      this.openingDetailsForm.addControl(`${openingDetails.getDayName.toLowerCase()}Start`, new FormControl(openingDetails.getStartTime, [Validators.required]))
      this.openingDetailsForm.addControl(`${openingDetails.getDayName.toLowerCase()}End`, new FormControl(openingDetails.getEndTime, [Validators.required]))
    })
  }

  saveDetails() {
    //Sima details:
    this.otherService.updateDetails(new Details(1, this.detailsForm.controls["address"].value, this.detailsForm.controls["phone"].value, this.detailsForm.controls["email"].value, this.detailsForm.controls["firePhone"].value)).subscribe({
      next: response => console.log(response),
      error: error => console.log(error)
    })

    //Nyitvatartás:
    for (let i: number = 0; i < this.dayNames.length; i++) {
      this.otherService.openingDetails[i].setStartTime = this.openingDetailsForm.controls[`${this.dayNames[i].toLowerCase()}Start`].value
      this.otherService.openingDetails[i].setEndTime = this.openingDetailsForm.controls[`${this.dayNames[i].toLowerCase()}End`].value
    }

    this.otherService.updateOpeningDetails().subscribe({
      next: response => console.log(response),
      error: error => console.log(error)
    })
  }
}
