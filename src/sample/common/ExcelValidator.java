package sample.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.Optional;

@Slf4j
public class ExcelValidator {

    public static Optional<Boolean> checker(File file){
        try {
            if(WorkbookFactory.create(file) != null){
                log.info(file.getName() + " is right excel file");
                return Optional.of(true);
            }else{
                log.error(file.getName() + " is Invalid excel file");
                return Optional.of(false);
            }

        }catch (Exception e){
            log.error(file.getName() + " is Invalid input file Or Not a valid Excel file");
            return Optional.of(false);
        }
    }

    public static void checkerWithError(File file){
        try {
            if(WorkbookFactory.create(file) != null){
                log.info(file.getName() + " is right excel file");
            }else{
                log.error(file.getName() + " is Invalid excel file");
                throw new IllegalArgumentException(file.getName() + " is Invalid excel file");
            }
        }catch (Exception e){
            log.error(file.getName() + " is Invalid input file Or Not a valid Excel file");
            throw new IllegalArgumentException(file.getName() + " is Invalid input file Or Not a valid Excel file");
        }
    }


}
