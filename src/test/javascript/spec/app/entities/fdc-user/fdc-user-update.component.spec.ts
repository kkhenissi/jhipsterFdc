import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterFdcTestModule } from '../../../test.module';
import { FdcUserUpdateComponent } from 'app/entities/fdc-user/fdc-user-update.component';
import { FdcUserService } from 'app/entities/fdc-user/fdc-user.service';
import { FdcUser } from 'app/shared/model/fdc-user.model';

describe('Component Tests', () => {
  describe('FdcUser Management Update Component', () => {
    let comp: FdcUserUpdateComponent;
    let fixture: ComponentFixture<FdcUserUpdateComponent>;
    let service: FdcUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterFdcTestModule],
        declarations: [FdcUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FdcUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FdcUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FdcUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FdcUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FdcUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
