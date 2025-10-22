import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectCompany } from './select-company';

describe('SelectCompany', () => {
  let component: SelectCompany;
  let fixture: ComponentFixture<SelectCompany>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectCompany]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectCompany);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
