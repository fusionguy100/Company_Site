import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProjectModal } from './edit-project-modal';

describe('EditProjectModal', () => {
  let component: EditProjectModal;
  let fixture: ComponentFixture<EditProjectModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditProjectModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditProjectModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
